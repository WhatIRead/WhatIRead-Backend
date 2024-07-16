package com.mvp1.whatiread.repository;

import com.mvp1.whatiread.entity.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendShipRepository extends JpaRepository<FriendRequest, Long> {

}
