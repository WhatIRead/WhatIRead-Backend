package com.mvp1.whatiread.service;

import com.mvp1.whatiread.dto.ApiResponse;
import com.mvp1.whatiread.dto.FriendDTO;
import org.springframework.http.ResponseEntity;

public interface FriendService {

  ResponseEntity<ApiResponse> addFriendRequest(Long userId, FriendDTO friendRequests);

  ResponseEntity<ApiResponse> modifyFriendRequest(Long userId, FriendDTO friendRequests);

  ResponseEntity<ApiResponse> deleteFriendRequest(Long userId, FriendDTO friendRequest);
}
