package com.mvp1.whatiread.controller;

import com.mvp1.whatiread.model.BookDetails;
import com.mvp1.whatiread.service.OpenLibraryService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/book")
public class BookController {

  private final OpenLibraryService openLibraryService;

  public BookController(OpenLibraryService openLibraryService) {
    this.openLibraryService = openLibraryService;
  }

  @GetMapping("getBooksWithTitle")
  public ResponseEntity<List<BookDetails>> getBooksWithTitle(
      @RequestParam("title") String title) {
    return new ResponseEntity<>(openLibraryService.searchBooksByTitle(title), HttpStatus.ACCEPTED);
  }
}
