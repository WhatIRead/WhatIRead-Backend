package com.mvp1.whatiread.controller;

import com.mvp1.whatiread.entity.Shelf;
import com.mvp1.whatiread.repository.ShelfRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shelf")
public class ShelfController {

  private ShelfRepository shelfRepository;

  public ShelfController(ShelfRepository shelfRepository) {
    this.shelfRepository = shelfRepository;
  }

  @GetMapping("/{userId}")
  public List<Shelf> getAllShelvesForUser(@PathVariable Long userId) {
    return null;
  }


}
