package com.mvp1.whatiread.controller;

import static com.mvp1.whatiread.utils.Utils.AUTH_BASE_PATH;
import static com.mvp1.whatiread.utils.Utils.SIGN_IN_PATH;
import static com.mvp1.whatiread.utils.Utils.SIGN_UP_PATH;
import static com.mvp1.whatiread.utils.Utils.USER_ROLE_NOT_SET;

import com.mvp1.whatiread.dto.ApiResponse;
import com.mvp1.whatiread.dto.JwtAuthenticationResponse;
import com.mvp1.whatiread.dto.LoginRequest;
import com.mvp1.whatiread.dto.SignUpRequest;
import com.mvp1.whatiread.entity.role.Role;
import com.mvp1.whatiread.entity.role.RoleName;
import com.mvp1.whatiread.entity.user.User;
import com.mvp1.whatiread.exception.AppException;
import com.mvp1.whatiread.exception.WhatIReadException;
import com.mvp1.whatiread.repository.RoleRepository;
import com.mvp1.whatiread.repository.UserRepository;
import com.mvp1.whatiread.security.JwtTokenProvider;
import jakarta.validation.Valid;
import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(AUTH_BASE_PATH)
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;

  public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
      RoleRepository roleRepository, PasswordEncoder passwordEncoder,
      JwtTokenProvider jwtTokenProvider) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @PostMapping(SIGN_IN_PATH)
  public ResponseEntity<JwtAuthenticationResponse> authenticateUser(
      @Valid @RequestBody LoginRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(),
            loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream().map(auth -> auth.getAuthority())
        .toList();
    String jwt = jwtTokenProvider.generateTokenFromUsername(
        (UserDetails) authentication.getPrincipal());

    return ResponseEntity.ok(new JwtAuthenticationResponse(userDetails.getUsername(), roles, jwt));
  }

  @PostMapping(SIGN_UP_PATH)
  public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
    if (Boolean.TRUE.equals(userRepository.existsByUsername(signUpRequest.getUsername()))) {
      throw new WhatIReadException(HttpStatus.BAD_REQUEST, "Username is already taken");
    }

    if (Boolean.TRUE.equals(userRepository.existsByEmail(signUpRequest.getEmail()))) {
      throw new WhatIReadException(HttpStatus.BAD_REQUEST, "Email is already taken");
    }

    String firstName = signUpRequest.getFirstName().toLowerCase();

    String lastName = signUpRequest.getLastName().toLowerCase();

    String username = signUpRequest.getUsername().toLowerCase();

    String email = signUpRequest.getEmail().toLowerCase();

    String password = passwordEncoder.encode(signUpRequest.getPassword());

    User user = new User(firstName, lastName, username, email, password);

    List<Role> roles = new ArrayList<>();

    if (userRepository.count() == 0) {
      roles.add(roleRepository.findByName(RoleName.ROLE_USER)
          .orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));
      roles.add(roleRepository.findByName(RoleName.ROLE_ADMIN)
          .orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));
    } else {
      roles.add(roleRepository.findByName(RoleName.ROLE_USER)
          .orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));
    }

    user.setRoles(roles);
    user.setCreatedAt(Instant.now());
    user.setUpdatedAt(Instant.now());

    User result = userRepository.save(user);

    URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/{userId}")
        .buildAndExpand(result.getId()).toUri();

    return ResponseEntity.created(location)
        .body(new ApiResponse(Boolean.TRUE, String.format("%s registered successfully", username)));
  }
}
