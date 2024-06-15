package com.mvp1.whatiread.service;


import com.mvp1.whatiread.dto.BookDTO;
import java.util.List;

public interface BookService {

  List<BookDTO> getBooksWithTitle(String title);
}
