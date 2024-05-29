package com.mvp1.whatiread.service;

import com.mvp1.whatiread.entity.user.User;
import com.mvp1.whatiread.payload.ApiResponse;
import com.mvp1.whatiread.payload.InfoRequest;
import com.mvp1.whatiread.payload.UserIdentityAvailability;
import com.mvp1.whatiread.payload.UserProfile;
import com.mvp1.whatiread.payload.UserSummary;
import com.mvp1.whatiread.security.UserPrincipal;

public interface UserService {

  UserSummary getCurrentUser(UserPrincipal currentUser);

  UserIdentityAvailability checkUsernameAvailability(String username);

  UserIdentityAvailability checkEmailAvailability(String email);

  UserProfile getUserProfile(String username);

  User addUser(User user);

  User updateUser(User newUser, String username, UserPrincipal currentUser);

  ApiResponse deleteUser(String username, UserPrincipal currentUser);

  ApiResponse giveAdmin(String username);

  ApiResponse removeAdmin(String username);

  UserProfile setOrUpdateInfo(UserPrincipal currentUser, InfoRequest infoRequest);

}