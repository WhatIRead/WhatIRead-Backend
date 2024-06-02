package com.mvp1.whatiread.service.impl;

import com.mvp1.whatiread.converter.BookConverter;
import com.mvp1.whatiread.dto.BookDTO;
import com.mvp1.whatiread.repository.BookRepository;
import com.mvp1.whatiread.service.BookService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

  private BookRepository bookRepository;
  private BookConverter bookConverter;

  @Autowired
  public BookServiceImpl(BookRepository bookRepository, BookConverter bookConverter) {
    this.bookRepository = bookRepository;
    this.bookConverter = bookConverter;
  }

  @Override
  public List<BookDTO> getBooksWithTitle(String title) {

    return null;
  }

//  public void addBookToShelf(long shelfId, Book book) {
//    Optional<Shelf> shelfOptional = shelfRepository.findByShelfId(shelfId);
//    if (shelfOptional.isPresent()) {
//      Shelf shelf = shelfOptional.get();
//      shelf.getBooksInShelf().add(book);
//      shelfRepository.save(shelf); // Persist changes to the database
//    }
//  }
}
