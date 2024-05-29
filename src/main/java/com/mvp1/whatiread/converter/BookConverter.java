package com.mvp1.whatiread.converter;

import com.mvp1.whatiread.dto.BookDTO;
import com.mvp1.whatiread.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookConverter {

  public BookDTO convertEntityToDTO(Book book) {
    BookDTO bookDTO = new BookDTO();
    bookDTO.setIsbn(book.getIsbn());
    return bookDTO;
  }
}
