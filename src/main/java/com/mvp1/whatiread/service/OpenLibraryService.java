package com.mvp1.whatiread.service;

import com.mvp1.whatiread.model.BookDetails;
import java.util.List;

public interface OpenLibraryService {

  public List<BookDetails> searchBooksByTitle(String title);
}
