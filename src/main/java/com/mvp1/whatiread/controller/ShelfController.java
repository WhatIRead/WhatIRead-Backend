package com.mvp1.whatiread.controller;

import com.mvp1.whatiread.entity.Shelf;
import com.mvp1.whatiread.payload.ShelfRequest;
import com.mvp1.whatiread.repository.ShelfRepository;
import com.mvp1.whatiread.service.ShelfService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shelf")
public class ShelfController {

  private ShelfRepository shelfRepository;
  private ShelfService shelfService;

  public ShelfController(ShelfRepository shelfRepository, ShelfService shelfService) {
    this.shelfRepository = shelfRepository;
    this.shelfService = shelfService;
  }

  @GetMapping("/{userId}")
  public List<Shelf> getAllShelvesForUser(@PathVariable Long userId) {
    return shelfService.getAllShelvesForUser(userId);
  }

  @GetMapping("/add")
  public void addShelf(@Valid @RequestBody ShelfRequest shelfRequest) {
    shelfService.addShelf(shelfRequest);

  }


}
