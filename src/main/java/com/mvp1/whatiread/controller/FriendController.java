package com.mvp1.whatiread.controller;

import com.mvp1.whatiread.dto.ApiResponse;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/friend")
public class FriendController {

  private final UserService userService;
  private final FriendService friendService;

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
  public ResponseEntity<String> changeFriendStatus(@CurrentUser UserPrincipal userPrincipal,
      @Valid @RequestBody FriendDTO friend) {
    return null;
  }

  @PostMapping("/addFriendRequest")
  @Schema(description = "Add friend request")
  public ResponseEntity<ApiResponse> addFriendRequest(@CurrentUser UserPrincipal currentUser,
      @Valid @RequestBody FriendDTO friendRequest) {
    return friendService.addFriendRequest(currentUser.getId(), friendRequest);
  }

  @PutMapping("/modifyFriendRequest")
  @Schema(description = "Modify friend request")
  public ResponseEntity<ApiResponse> modifyFriendRequest(@CurrentUser UserPrincipal currentUser,
      @Valid @RequestBody FriendDTO friendRequest) {
    return friendService.modifyFriendRequest(currentUser.getId(), friendRequest);
  }

  @DeleteMapping("/deleteFriendRequest")
  @Schema(description = "Delete friend request")
  public ResponseEntity<ApiResponse> deleteFriendRequest(@CurrentUser UserPrincipal currentUser,
      @Valid @RequestBody FriendDTO friendRequest) {
    return friendService.deleteFriendRequest(currentUser.getId(), friendRequest);
  }

  @GetMapping("/getSentFriendRequests")
  @Schema(description = "Get all sent friend requests friend requests.")
  public ResponseEntity<List<FriendDTO>> getSendFriendRequests(@CurrentUser UserPrincipal currentUser) {
    return null;
  }

  @GetMapping("/getReceivedFriendRequests")
  @Schema(description = "Get all received requests friend requests.")
  public ResponseEntity<List<FriendDTO>> getReceivedFriendRequests(@CurrentUser UserPrincipal currentUser) {
    return null;
  }
}
