package com.mvp1.whatiread.controller;

import com.mvp1.whatiread.dto.ApiResponse;
import com.mvp1.whatiread.dto.InfoRequest;
import com.mvp1.whatiread.dto.UserIdentityAvailability;
import com.mvp1.whatiread.dto.UserProfile;
import com.mvp1.whatiread.dto.UserSummary;
import com.mvp1.whatiread.entity.user.User;
import com.mvp1.whatiread.security.CurrentUser;
import com.mvp1.whatiread.security.UserPrincipal;
import com.mvp1.whatiread.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Service", description = "This service provides ability to handle user related information.")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/me")
  @PreAuthorize("hasRole('USER')")
  @Operation(description = "Returns current user details.")
  public ResponseEntity<UserSummary> getCurrentUser(@CurrentUser UserPrincipal currentUser) {
    UserSummary userSummary = userService.getCurrentUser(currentUser);
    return new ResponseEntity<>(userSummary, HttpStatus.OK);
  }

  @GetMapping("/checkUsernameAvailability")
  @Operation(description = "This api checks if a given username is available to use or not.")
  public ResponseEntity<UserIdentityAvailability> checkUsernameAvailability(
      @RequestParam(value = "username") String username) {
    UserIdentityAvailability userIdentityAvailability = userService.checkUsernameAvailability(
        username);

    return new ResponseEntity<>(userIdentityAvailability, HttpStatus.OK);
  }

  @GetMapping("/checkEmailAvailability")
  @Operation(description = "This api checks if a given email is available to use or not.")
  public ResponseEntity<UserIdentityAvailability> checkEmailAvailability(
      @RequestParam(value = "email") String email) {
    UserIdentityAvailability userIdentityAvailability = userService.checkEmailAvailability(email);
    return new ResponseEntity<>(userIdentityAvailability, HttpStatus.OK);
  }

  @GetMapping("/{username}/profile")
  @Operation(description = "This api returns a given user profile details.")
  public ResponseEntity<UserProfile> getUSerProfile(
      @PathVariable(value = "username") String username) {
    UserProfile userProfile = userService.getUserProfile(username);
    return new ResponseEntity<>(userProfile, HttpStatus.OK);
  }

  @PostMapping("/add")
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(description = "This api adds ADMIN role to given user. This operation can be performed only by an admin")
  public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
    User newUser = userService.addUser(user);
    return new ResponseEntity<>(newUser, HttpStatus.CREATED);
  }

  @PutMapping("/{username}")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  @Operation(description = "This api updates given user details. Both user or admin can perform this operation.")
  public ResponseEntity<User> updateUser(@Valid @RequestBody User newUser,
      @PathVariable(value = "username") String username, @CurrentUser UserPrincipal currentUser) {
    User updatedUSer = userService.updateUser(newUser, username, currentUser);

    return new ResponseEntity<>(updatedUSer, HttpStatus.CREATED);
  }

  @DeleteMapping("/{username}")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public ResponseEntity<ApiResponse> deleteUser(@PathVariable(value = "username") String username,
      @CurrentUser UserPrincipal currentUser) {
    ApiResponse apiResponse = userService.deleteUser(username, currentUser);

    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }

  @PutMapping("/{username}/giveAdmin")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse> giveAdmin(@PathVariable(name = "username") String username) {
    ApiResponse apiResponse = userService.giveAdmin(username);

    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }

  @PutMapping("/{username}/takeAdmin")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse> takeAdmin(@PathVariable(name = "username") String username) {
    ApiResponse apiResponse = userService.removeAdmin(username);

    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }

  @PutMapping("/setOrUpdateInfo")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public ResponseEntity<UserProfile> setAddress(@CurrentUser UserPrincipal currentUser,
      @Valid @RequestBody InfoRequest infoRequest) {
    UserProfile userProfile = userService.setOrUpdateInfo(currentUser, infoRequest);

    return new ResponseEntity<>(userProfile, HttpStatus.OK);
  }
}
