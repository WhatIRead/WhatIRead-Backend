package com.mvp1.whatiread.service;

import com.mvp1.whatiread.entity.Book;
import com.mvp1.whatiread.entity.Shelf;
import com.mvp1.whatiread.dto.ShelfRequest;
import java.util.List;

public interface ShelfService {

  List<Book> getAllBookFromShelf(long shelfId);

  List<Shelf> getAllShelvesForUser(Long userId);

  Shelf getShelfForUser(Long userId, Long shelfId);

  void addShelf(Long userId, ShelfRequest shelf);

  void deleteShelf(long shelfId);
}
