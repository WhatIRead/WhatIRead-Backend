package com.mvp1.whatiread.service;

import com.mvp1.whatiread.entity.Book;
import com.mvp1.whatiread.entity.Shelf;
import com.mvp1.whatiread.payload.ShelfRequest;
import java.util.List;

public interface ShelfService {

  public List<Book> getAllBookFromShelf(long shelfId);

  public List<Shelf> getAllShelvesForUser(Long userId);

  public void addShelf(ShelfRequest shelf);

  public void deleteShelf(long shelfId);
}
