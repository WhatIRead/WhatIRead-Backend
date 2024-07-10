package com.mvp1.whatiread.service.impl;

import com.mvp1.whatiread.dto.ShelfDTO;
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
import java.util.Set;
import java.util.stream.Collectors;
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
  public Set<Book> getAllBookFromShelf(long shelfId) {
    return shelfRepository.findBooksById(shelfId);
  }

  @Override
  public Set<ShelfDTO> getAllShelvesForUser(Long userId) {
    return shelfRepository.findByUserId(userId).stream()
        .map(Utils::convertShelfToShelfDTO).collect(Collectors.toSet());
  }

  @Override
  public ShelfDTO getShelfForUser(Long userId, Long shelfId) {
    return Utils.convertShelfToShelfDTO(shelfRepository.findByIdAndUserId(userId, shelfId));
  }

  @Override
  public void addShelf(Long userId, ShelfDTO shelfRequest) {
    User user = userRepository.findById(userId).orElse(null);
    if (user == null) {
      return;
    }
    Shelf shelf = Utils.convertShelfDTOToShelf(shelfRequest);
    shelf.setUser(user);
    shelf.setCreatedAt(LocalDateTime.now());
    shelfRepository.save(shelf);
  }

  @Override
  public void deleteShelf(long shelfId) {
    shelfRepository.deleteById(shelfId);
  }
}