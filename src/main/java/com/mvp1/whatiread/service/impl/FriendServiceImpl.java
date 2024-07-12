package com.mvp1.whatiread.service.impl;

import com.mvp1.whatiread.dto.FriendDTO;
import com.mvp1.whatiread.entity.FriendShip;
import com.mvp1.whatiread.entity.user.User;
import com.mvp1.whatiread.repository.FriendShipRepository;
import com.mvp1.whatiread.repository.UserRepository;
import com.mvp1.whatiread.service.FriendService;
import com.mvp1.whatiread.utils.Utils;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class FriendServiceImpl implements FriendService {

  private final UserRepository userRepository;
  private final FriendShipRepository friendShipRepository;

  public FriendServiceImpl(UserRepository userRepository,
      FriendShipRepository friendShipRepository) {
    this.userRepository = userRepository;
    this.friendShipRepository = friendShipRepository;
  }

  /**
   * @param userId
   * @param friendRequests
   */
  @Override
  public void addFriendRequests(Long userId, Set<FriendDTO> friendRequests) {
    User user = userRepository.findById(userId).orElse(null);
    if (user == null) {
      return;
    }
    Set<FriendShip> friends = friendRequests.stream()
        .map(friendRequest -> {
          FriendShip friendShip = Utils.modelMapper.map(friendRequest, FriendShip.class);
          friendShip.setUser(user);
          friendShip.setFriend(userRepository.findByUsername(friendRequest.getUserName()).orElse(null));
          return friendShip;
        })
        .filter(friend -> friend.getFriend()!= null).collect(Collectors.toSet());
    friendShipRepository.saveAll(friends);
  }
}
