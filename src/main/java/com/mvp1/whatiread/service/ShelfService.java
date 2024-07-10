package com.mvp1.whatiread.service;

import com.mvp1.whatiread.dto.ShelfDTO;
import com.mvp1.whatiread.entity.Book;
import java.util.Set;

public interface ShelfService {

  Set<Book> getAllBookFromShelf(long shelfId);

  Set<ShelfDTO> getAllShelvesForUser(Long userId);

  ShelfDTO getShelfForUser(Long userId, Long shelfId);

  void addShelf(Long userId, ShelfDTO shelf);

  void deleteShelf(long shelfId);
}
