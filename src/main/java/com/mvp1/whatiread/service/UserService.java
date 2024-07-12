package com.mvp1.whatiread.service;

import com.mvp1.whatiread.dto.ApiResponse;
import com.mvp1.whatiread.dto.InfoRequest;
import com.mvp1.whatiread.dto.UserIdentityAvailability;
import com.mvp1.whatiread.dto.UserProfile;
import com.mvp1.whatiread.dto.UserSummary;
import com.mvp1.whatiread.entity.user.User;
import com.mvp1.whatiread.security.UserPrincipal;
import java.util.Set;

public interface UserService {

  Set<UserProfile> getUsersWithGivenUsername(String username);

  UserSummary getCurrentUser(UserPrincipal currentUser);

  UserIdentityAvailability checkUsernameAvailability(String username);

  UserIdentityAvailability checkEmailAvailability(String email);

  UserSummary getUserProfile(String username);

  User addUser(User user);

  User updateUser(User newUser, String username, UserPrincipal currentUser);

  ApiResponse deleteUser(String username, UserPrincipal currentUser);

  ApiResponse giveAdmin(String username);

  ApiResponse removeAdmin(String username);

  UserProfile setOrUpdateInfo(UserPrincipal currentUser, InfoRequest infoRequest);

}