package com.mvp1.whatiread.service.impl;

import com.mvp1.whatiread.entity.Book;
import com.mvp1.whatiread.entity.Shelf;
import com.mvp1.whatiread.dto.ShelfRequest;
import com.mvp1.whatiread.repository.BookRepository;
import com.mvp1.whatiread.repository.ShelfRepository;
import com.mvp1.whatiread.service.ShelfService;
import com.mvp1.whatiread.service.UserService;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ShelfServiceImpl implements ShelfService {

  private final ShelfRepository shelfRepository;
  private final BookRepository bookRepository;
  private final UserService userService;
  private ModelMapper modelMapper;

  public ShelfServiceImpl(ShelfRepository shelfRepository,
      BookRepository bookRepository, UserService userService) {
    this.shelfRepository = shelfRepository;
    this.bookRepository = bookRepository;
    this.userService = userService;
    this.modelMapper = new ModelMapper();
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
  public Shelf getShelfForUser(Long userId, Long shelfId) {
    return shelfRepository.findByIdAndUserId(userId, shelfId);
  }

  @Override
  public void addShelf(Long userId, ShelfRequest shelfRequest) {
    Shelf shelf = modelMapper.map(shelfRequest, Shelf.class);
    shelfRepository.save(shelf);
  }

  @Override
  public void deleteShelf(long shelfId) {
    shelfRepository.deleteById(shelfId);
  }
}