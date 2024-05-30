package com.mvp1.whatiread.service.Impl;

import com.mvp1.whatiread.entity.Book;
import com.mvp1.whatiread.entity.Shelf;
import com.mvp1.whatiread.payload.ShelfRequest;
import com.mvp1.whatiread.repository.BookRepository;
import com.mvp1.whatiread.repository.ShelfRepository;
import com.mvp1.whatiread.service.ShelfService;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ShelfServiceImpl implements ShelfService {

  private ShelfRepository shelfRepository;
  private final BookRepository bookRepository;
  private ModelMapper modelMapper;

  public ShelfServiceImpl(ShelfRepository shelfRepository,
      BookRepository bookRepository) {
    this.shelfRepository = shelfRepository;
    this.bookRepository = bookRepository;
  }

  @Override
  public List<Book> getAllBookFromShelf(long shelfId) {
    return shelfRepository.findBooksById(shelfId);
  }

  @Override
  public List<Shelf> getAllShelvesForUser(Long userId) {
    return shelfRepository.findByUserId(userId);
  }

  @Override
  public void addShelf(ShelfRequest shelfRequest) {
    Shelf shelf = new Shelf();
    modelMapper.map(shelfRequest, shelf);
    shelfRepository.save(shelf);
  }

  @Override
  public void deleteShelf(long shelfId) {
    shelfRepository.deleteById(shelfId);
  }
}