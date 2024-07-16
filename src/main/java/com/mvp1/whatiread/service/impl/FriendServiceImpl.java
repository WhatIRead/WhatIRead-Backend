package com.mvp1.whatiread.service.impl;

import com.mvp1.whatiread.dto.ApiResponse;
import com.mvp1.whatiread.dto.FriendDTO;
import com.mvp1.whatiread.entity.FriendRequest;
import com.mvp1.whatiread.entity.user.User;
import com.mvp1.whatiread.exception.AppException;
import com.mvp1.whatiread.exception.Resource;
import com.mvp1.whatiread.exception.ResourceNotFoundException;
import com.mvp1.whatiread.repository.FriendRequestRepository;
import com.mvp1.whatiread.repository.UserRepository;
import com.mvp1.whatiread.service.FriendService;
import com.mvp1.whatiread.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FriendServiceImpl implements FriendService {

  private final UserRepository userRepository;
  private final FriendRequestRepository friendrequestRepository;

  public FriendServiceImpl(UserRepository userRepository,
      FriendRequestRepository friendrequestRepository) {
    this.userRepository = userRepository;
    this.friendrequestRepository = friendrequestRepository;
  }

  private User getUser(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
  }

  private Long getFriendRequestId(Long userId, FriendDTO friendRequest, User user, User friend) {
    return friendrequestRepository.findByUserIdAndFriendId(userId, friend.getId())
        .orElseThrow(() -> {
          List<Resource> resources = new ArrayList<>();
          Resource r1 = new Resource("User", "username", user.getUsername());
          Resource r2 = new Resource("Friend", "username", friend.getUsername());
          resources.add(r1);
          resources.add(r2);
          return new ResourceNotFoundException(resources);
        });
  }

  /**
   * @param userId
   * @param friendRequest
   * @return
   */
  @Override
  public ResponseEntity<ApiResponse> addFriendRequest(Long userId, FriendDTO friendRequest) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new AppException("User not found"));
    FriendRequest friend = Utils.modelMapper.map(friendRequest, FriendRequest.class);
    friend.setUser(user);
    friend.setFriend(userRepository.findByUsername(friendRequest.getUserName()).orElseThrow(
        () -> new ResourceNotFoundException("User", "userName", friendRequest.getUserName())));
    friendrequestRepository.save(friend);
    return new ResponseEntity<>(new ApiResponse(Boolean.TRUE,
        "Friend request for " + friendRequest.getUserName() + " created successfully."),
        HttpStatus.CREATED);
  }

  public ResponseEntity<ApiResponse> modifyFriendRequest(Long userId, FriendDTO friendRequest) {
    User user = getUser(userId);
    User friend = userRepository.findByUsername(friendRequest.getUserName()).orElseThrow(
        () -> new ResourceNotFoundException("User", "userName", friendRequest.getUserName()));
    FriendRequest request = Utils.modelMapper.map(friendRequest, FriendRequest.class);
    request.setUser(user);
    request.setFriend(friend);
    request.setId(getFriendRequestId(userId, friendRequest, user, friend));
    friendrequestRepository.save(request);
    return new ResponseEntity<>(new ApiResponse(Boolean.TRUE,
        "Friend request for " + friendRequest.getUserName() + " modified successfully."),
        HttpStatus.OK);
  }

  public ResponseEntity<ApiResponse> deleteFriendRequest(Long userId, FriendDTO friendRequest) {
    User user = getUser(userId);
    User friend = userRepository.findByUsername(friendRequest.getUserName()).orElseThrow(
        () -> new ResourceNotFoundException("User", "userName", friendRequest.getUserName()));
    friendrequestRepository.deleteById(getFriendRequestId(userId, friendRequest, user, friend));
    return new ResponseEntity<>(new ApiResponse(Boolean.TRUE,
        "Friend request for " + friendRequest.getUserName() + " deleted successfully."),
        HttpStatus.OK);
  }
}
