package com.mvp1.whatiread.service;

import com.mvp1.whatiread.dto.FriendDTO;
import java.util.Set;

public interface FriendService {

  void addFriendRequests(Long userId, Set<FriendDTO> friendRequests);
}
