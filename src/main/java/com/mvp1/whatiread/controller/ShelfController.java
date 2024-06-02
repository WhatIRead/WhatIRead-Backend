package com.mvp1.whatiread.controller;

import com.mvp1.whatiread.entity.Shelf;
import com.mvp1.whatiread.exception.AccessDeniedException;
import com.mvp1.whatiread.payload.ShelfRequest;
import com.mvp1.whatiread.repository.ShelfRepository;
import com.mvp1.whatiread.security.CurrentUser;
import com.mvp1.whatiread.security.UserPrincipal;
import com.mvp1.whatiread.service.ShelfService;
import com.mvp1.whatiread.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shelf")
@Tag(name = "Shelf Service", description = "This service handles requests related to shelves")
public class ShelfController {

  private final ShelfRepository shelfRepository;
  private final ShelfService shelfService;
  private final UserService userService;

  public ShelfController(ShelfRepository shelfRepository, ShelfService shelfService,
      UserService userService) {
    this.shelfRepository = shelfRepository;
    this.shelfService = shelfService;
    this.userService = userService;
  }

  @GetMapping("/{userId}/shelves")
  @Operation(description = "Returns all the shelves of given user.")
  public List<Shelf> getAllShelvesForUser(@PathVariable Long userId,
      @CurrentUser UserPrincipal currentUser) {
    if (userService.getCurrentUser(currentUser).getId() != userId) {
      throw new AccessDeniedException("You can't access this shelf!");
    }
    return shelfService.getAllShelvesForUser(userId);
  }

  @GetMapping("/add")
  @Operation(description = "Adds a shelf to given user.")
  public void addShelf(@Valid @RequestBody ShelfRequest shelfRequest) {
    shelfService.addShelf(shelfRequest);
  }

  @GetMapping("/{userId}/shelf/{shelfId}")
  @Operation(description = "Returns a shelf with given shelf-id of given user.")
  public Shelf getShelfForUser(@PathVariable Long userId, @PathVariable Long shelfId,
      @CurrentUser UserPrincipal currentUser) {
    Shelf shelf = shelfService.getShelfForUser(userId, shelfId);
    if (userService.getCurrentUser(currentUser).getId() != userId && Boolean.TRUE.equals(
        !shelf.getIsPublic())) {
      throw new AccessDeniedException("You can't access this shelf!");
    }
    return shelf;
  }


}
