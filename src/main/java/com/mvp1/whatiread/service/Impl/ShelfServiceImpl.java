package com.mvp1.whatiread.service.Impl;

import com.mvp1.whatiread.entity.Book;
import com.mvp1.whatiread.entity.Shelf;
import com.mvp1.whatiread.repository.ShelfRepository;
import com.mvp1.whatiread.service.ShelfService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ShelfServiceImpl implements ShelfService {

  private ShelfRepository shelfRepository;

  public ShelfServiceImpl(ShelfRepository shelfRepository) {
    this.shelfRepository = shelfRepository;
  }


  public List<Book> getAllBookFromShelf(long shelfId) {
    return null;
  }

  public List<Shelf> getAllShelvesForUser(Long userId) {
    return null;
  }

  public void addShelf(Shelf shelf) {
    shelfRepository.save(shelf);
  }

  public void deleteShelf(long shelfId) {
  }
}