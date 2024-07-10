package com.mvp1.whatiread.controller;

import com.mvp1.whatiread.dto.ShelfDTO;
import com.mvp1.whatiread.repository.ShelfRepository;
import com.mvp1.whatiread.security.CurrentUser;
import com.mvp1.whatiread.security.UserPrincipal;
import com.mvp1.whatiread.service.ShelfService;
import com.mvp1.whatiread.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

  @GetMapping("/shelves")
  @Operation(description = "Returns all the shelves of given user.")
  public ResponseEntity<Set<ShelfDTO>> getAllShelvesForUser(
      @CurrentUser UserPrincipal currentUser) {
    return new ResponseEntity<>(shelfService.getAllShelvesForUser(currentUser.getId()),
        HttpStatus.OK);
  }

  @PostMapping("/add")
  @Operation(description = "Adds a shelf to given user.")
  public ResponseEntity<String> addShelf(@CurrentUser UserPrincipal userPrincipal,
      @Valid @RequestBody ShelfDTO shelfRequest) {
    shelfService.addShelf(userPrincipal.getId(), shelfRequest);
    return new ResponseEntity<>(
        "Shelf " + shelfRequest.getName() + " created successfully for user "
            + userPrincipal.getUsername(), HttpStatus.CREATED);
  }

  @GetMapping("/shelf/{shelfId}")
  @Operation(description = "Returns a shelf with given shelf-id of given user.")
  public ResponseEntity<ShelfDTO> getShelfForUser(@PathVariable Long shelfId,
      @CurrentUser UserPrincipal currentUser) {
    return new ResponseEntity<>(shelfService.getShelfForUser(currentUser.getId(), shelfId),
        HttpStatus.OK);
  }


}
