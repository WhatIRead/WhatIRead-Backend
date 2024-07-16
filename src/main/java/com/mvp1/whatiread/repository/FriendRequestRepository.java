package com.mvp1.whatiread.repository;

import com.mvp1.whatiread.entity.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
  void deleteByFriendId(Long friendId);
  void deleteByFriendUsername(String username);
  boolean existsByUserIdAndFriendId(Long userId, Long friendId);
}
