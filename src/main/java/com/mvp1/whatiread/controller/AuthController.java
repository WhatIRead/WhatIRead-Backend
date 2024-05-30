package com.mvp1.whatiread.controller;

import static com.mvp1.whatiread.utils.Constants.USER_ROLE_NOT_SET;

import com.mvp1.whatiread.entity.role.Role;
import com.mvp1.whatiread.entity.role.RoleName;
import com.mvp1.whatiread.entity.user.User;
import com.mvp1.whatiread.exception.AppException;
import com.mvp1.whatiread.exception.WhatIReadException;
import com.mvp1.whatiread.payload.ApiResponse;
import com.mvp1.whatiread.payload.JwtAuthenticationResponse;
import com.mvp1.whatiread.payload.LoginRequest;
import com.mvp1.whatiread.payload.SignUpRequest;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private AuthenticationManager authenticationManager;
  private UserRepository userRepository;
  private RoleRepository roleRepository;
  private PasswordEncoder passwordEncoder;
  private JwtTokenProvider jwtTokenProvider;

  public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
      RoleRepository roleRepository, PasswordEncoder passwordEncoder,
      JwtTokenProvider jwtTokenProvider) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @GetMapping("/signin")
  public ResponseEntity<JwtAuthenticationResponse> authenticateUser(
      @Valid @RequestBody LoginRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(),
            loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = jwtTokenProvider.generateToken(authentication);
    return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
  }

  @GetMapping("/signup")
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
        .body(new ApiResponse(Boolean.TRUE, "User registered successfully"));
  }
}
