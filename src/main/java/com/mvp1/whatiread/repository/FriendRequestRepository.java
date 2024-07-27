package com.mvp1.whatiread.repository;

import com.mvp1.whatiread.entity.FriendRequest;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
  @Query("SELECT fr.id FROM FriendRequest fr WHERE fr.user.id = :userId AND fr.friend.id = :friendId AND fr.status='PENDING'")
  Optional<Long> findByUserIdAndFriendId(@Param("userId") Long userId, @Param("friendId") Long friendId);
}
