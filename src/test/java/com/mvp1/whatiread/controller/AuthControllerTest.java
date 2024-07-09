package com.mvp1.whatiread.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.javafaker.Faker;
import com.mvp1.whatiread.dto.ApiResponse;
import com.mvp1.whatiread.dto.JwtAuthenticationResponse;
import com.mvp1.whatiread.dto.LoginRequest;
import com.mvp1.whatiread.dto.SignUpRequest;
import com.mvp1.whatiread.entity.role.Role;
import com.mvp1.whatiread.entity.role.RoleName;
import com.mvp1.whatiread.exception.WhatIReadException;
import com.mvp1.whatiread.repository.RoleRepository;
import com.mvp1.whatiread.repository.UserRepository;
import com.mvp1.whatiread.security.JwtTokenProvider;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

class AuthControllerTest {

  private static final Logger log = LoggerFactory.getLogger(AuthControllerTest.class);
  @Mock
  private AuthenticationManager authenticationManager;
  @Mock
  private JwtTokenProvider jwtTokenProvider;
  @InjectMocks
  private AuthController authController;
  @Mock
  private Authentication authentication;
  private Faker faker;
  @Mock
  private UserRepository userRepository;
  @Mock
  private PasswordEncoder passwordEncoder;
  @Mock
  private RoleRepository roleRepository;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    faker = new Faker();
  }


  @Test
  void authenticateUser() {
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setUsernameOrEmail(faker.internet().emailAddress());
    loginRequest.setPassword(faker.internet().password());
    when(authenticationManager.authenticate(
        any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
    when(jwtTokenProvider.generateTokenFromUsername(any(UserDetails.class))).thenReturn(
        "fake-jwt-token");

    SecurityContext securityContext = mock(SecurityContext.class);
    SecurityContextHolder.setContext(securityContext);

    System.out.println(loginRequest);

    ResponseEntity<JwtAuthenticationResponse> response = authController.authenticateUser(
        loginRequest);

    // Assert
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("fake-jwt-token", response.getBody().getJwtToken());

    verify(authenticationManager, times(1)).authenticate(
        any(UsernamePasswordAuthenticationToken.class));
    verify(jwtTokenProvider, times(1)).generateTokenFromUsername(any(UserDetails.class));
    verify(securityContext, times(1)).setAuthentication(authentication);

  }

  @Test
  public void testRegisterUser_Success() {
    // Arrange
    String userName = faker.name().username();
    SignUpRequest signUpRequest = SignUpRequest.builder().firstName(faker.name().firstName())
        .lastName(faker.name().lastName()).username(userName).email(faker.internet()
            .emailAddress()).password(faker.internet().password()).build();
    when(userRepository.existsByUsername(userName)).thenReturn(false);
    when(roleRepository.count()).thenReturn(1L);
    when(roleRepository.findByName(RoleName.ROLE_USER)).thenReturn(
        Optional.of(new Role(1L, RoleName.ROLE_USER)));
    when(roleRepository.findByName(RoleName.ROLE_ADMIN)).thenReturn(
        Optional.of(new Role(2L, RoleName.ROLE_ADMIN)));

    System.out.println(signUpRequest);
    // Act
    ResponseEntity<ApiResponse> response = authController.registerUser(signUpRequest);

    // Assert
    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertTrue(Objects.requireNonNull(response.getBody()).isSuccess());
    assertEquals("User registered successfully", response.getBody().getMessage());
    verify(userRepository, times(1)).existsByUsername("newUser");
  }

  @Test
  public void testRegisterUser_UsernameAlreadyTaken() {
    SignUpRequest signUpRequest = SignUpRequest.builder().firstName(faker.name().firstName())
        .lastName(faker.name().lastName()).username(faker.name().username()).email(faker.internet()
            .emailAddress()).password(faker.internet().password()).build();
    when(userRepository.existsByUsername("newUser")).thenReturn(false);
    signUpRequest.setUsername("existingUser");
    when(userRepository.existsByUsername("existingUser")).thenReturn(true);

    // Act & Assert
    WhatIReadException exception = assertThrows(WhatIReadException.class,
        () -> authController.registerUser(signUpRequest));
    assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    assertEquals("Username is already taken", exception.getMessage());
    verify(userRepository, times(1)).existsByUsername("existingUser");
  }
}