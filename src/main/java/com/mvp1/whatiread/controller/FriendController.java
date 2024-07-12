package com.mvp1.whatiread.controller;

import com.mvp1.whatiread.dto.FriendDTO;
import com.mvp1.whatiread.dto.UserProfile;
import com.mvp1.whatiread.security.CurrentUser;
import com.mvp1.whatiread.security.UserPrincipal;
import com.mvp1.whatiread.service.FriendService;
import com.mvp1.whatiread.service.UserService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/friend")
public class FriendController {

  UserService userService;
  FriendService friendService;

  public FriendController(UserService userService, FriendService friendService) {
    this.userService = userService;
    this.friendService = friendService;
  }

  @GetMapping("/findUsers")
  @Schema(description = "Find users matching given text.")
  public ResponseEntity<Set<UserProfile>> findUsers(@RequestParam("userName") String userName) {
    return new ResponseEntity<>(userService.getUsersWithGivenUsername(userName), HttpStatus.OK);
  }

  @PostMapping("/changeFriendStatus")
  @Schema(description = "Accept, Reject friend request or remove a friend.")
  public ResponseEntity<String> changeFriendStatus(@Valid @RequestBody FriendDTO friend) {
    return null;
  }

  @PostMapping("/addFriends")
  @Schema(description = "Add friends")
  public ResponseEntity<String> addFriends(@CurrentUser UserPrincipal currentUser,
      @Valid @RequestBody Set<FriendDTO> friendRequests) {
    friendService.addFriendRequests(currentUser.getId(), friendRequests);
    return new ResponseEntity<>("Friends added Successfully", HttpStatus.OK);
  }
}
