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

  /**
   * @param userId
   * @param friendRequests
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
        "Friend request for " + friendRequest.getUserName() + " created Successfully."), HttpStatus.CREATED);
  }

  public ResponseEntity<ApiResponse> modifyFriendRequest(Long userId, FriendDTO friendRequest) {
    FriendRequest friend = Utils.modelMapper.map(friendRequest, FriendRequest.class);
    friend.setUser(getUser(userId));
    friend.setFriend(userRepository.findByUsername(friendRequest.getUserName()).orElseThrow(
        () -> new ResourceNotFoundException("User", "userName", friendRequest.getUserName())));
    friendrequestRepository.save(friend);
    if(!friendrequestRepository.existsByUserIdAndFriendId(friend.getUser().getId(), friend.getFriend().getId())){
      List<Resource> errors = new ArrayList<>();
      Resource r1 = new Resource("User", "username", friendRequest.getUserName());
      throw new ResourceNotFoundException("User", "username", friend.getUser().getUsername());
    }
    return new ResponseEntity<>(new ApiResponse(Boolean.TRUE,
        "Friend request for " + friendRequest.getUserName() + " created Successfully."),
        HttpStatus.OK);
  }

  public ResponseEntity<ApiResponse> deleteFriendRequest(Long userId, FriendDTO friendRequest) {
    User user = getUser(userId);
    FriendRequest friend = Utils.modelMapper.map(friendRequest, FriendRequest.class);
    friend.setUser(user);
    friend.setFriend(userRepository.findByUsername(friendRequest.getUserName()).orElseThrow(
        () -> new ResourceNotFoundException("User", "userName", friendRequest.getUserName())));
    friendrequestRepository.delete(friend);
    return new ResponseEntity<>(new ApiResponse(Boolean.TRUE,
        "Friend request for " + friendRequest.getUserName() + " deleted Successfully."), HttpStatus.OK);
  }
}
