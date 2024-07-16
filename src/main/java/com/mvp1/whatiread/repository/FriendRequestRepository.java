package com.mvp1.whatiread.repository;

import com.mvp1.whatiread.entity.FriendRequest;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
  void deleteByFriendId(Long friendId);
  void deleteByFriendUsername(String username);
  @Query("SELECT fr.id FROM FriendRequest fr WHERE fr.user.id = :userId AND fr.friend.id = :friendId")
  Optional<Long> findByUserIdAndFriendId(@Param("userId") Long userId, @Param("friendId") Long friendId);
  boolean existsByUserIdAndFriendId(Long userId, Long friendId);
}
