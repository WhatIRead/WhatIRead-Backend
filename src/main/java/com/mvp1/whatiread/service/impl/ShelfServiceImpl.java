package com.mvp1.whatiread.service.impl;

import com.mvp1.whatiread.dto.ShelfDto;
import com.mvp1.whatiread.entity.Book;
import com.mvp1.whatiread.entity.Shelf;
import com.mvp1.whatiread.entity.user.User;
import com.mvp1.whatiread.repository.BookRepository;
import com.mvp1.whatiread.repository.ShelfRepository;
import com.mvp1.whatiread.repository.UserRepository;
import com.mvp1.whatiread.service.ShelfService;
import com.mvp1.whatiread.service.UserService;
import com.mvp1.whatiread.utils.Utils;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ShelfServiceImpl implements ShelfService {

  private final ShelfRepository shelfRepository;
  private final BookRepository bookRepository;
  private final UserService userService;
  private final UserRepository userRepository;

  public ShelfServiceImpl(ShelfRepository shelfRepository,
      BookRepository bookRepository, UserService userService, UserRepository userRepository) {
    this.shelfRepository = shelfRepository;
    this.bookRepository = bookRepository;
    this.userService = userService;
    this.userRepository = userRepository;
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
  public void addShelf(Long userId, ShelfDto shelfRequest) {
    User user = userRepository.findById(userId).orElse(null);
    if (user == null) {
      return;
    }
    Shelf shelf = Utils.modelMapper.map(shelfRequest, Shelf.class);
    shelf.setUser(user);
    shelf.setCreatedAt(LocalDateTime.now());
    shelfRepository.save(shelf);
  }

  @Override
  public void deleteShelf(long shelfId) {
    shelfRepository.deleteById(shelfId);
  }
}