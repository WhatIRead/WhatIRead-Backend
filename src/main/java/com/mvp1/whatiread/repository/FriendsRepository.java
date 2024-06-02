package com.mvp1.whatiread.repository;

import com.mvp1.whatiread.entity.FriendShip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendsRepository extends JpaRepository<FriendShip, Long> {

}
