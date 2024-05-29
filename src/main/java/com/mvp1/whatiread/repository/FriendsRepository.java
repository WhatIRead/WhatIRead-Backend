package com.mvp1.whatiread.repository;

import com.mvp1.whatiread.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendsRepository extends JpaRepository<Friend, Long> {

}
