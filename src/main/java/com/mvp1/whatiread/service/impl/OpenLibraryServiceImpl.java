package com.mvp1.whatiread.service.impl;

import static com.mvp1.whatiread.utils.Constants.IMAGE_SEARCH_URL;

import com.mvp1.whatiread.client.OpenLibraryClient;
import com.mvp1.whatiread.model.BookDetails;
import com.mvp1.whatiread.model.OpenLibResponse;
import com.mvp1.whatiread.service.OpenLibraryService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenLibraryServiceImpl implements OpenLibraryService {

  private final OpenLibraryClient openLibraryClient;

  @Autowired
  public OpenLibraryServiceImpl(OpenLibraryClient openLibraryClient) {
    this.openLibraryClient = openLibraryClient;
  }

  public List<BookDetails> searchBooksByTitle(String title) {
    OpenLibResponse response = openLibraryClient.searchBooksByTitle(title, "title,isbn,author_name",
        10);
    System.out.println(response);
    return mapToBookDetails(response);
  }

  private List<BookDetails> mapToBookDetails(OpenLibResponse response) {
    return response.getDocs().stream()
        .map(doc -> {
          BookDetails bookDetails = new BookDetails();
          bookDetails.setTitle(doc.getTitle());
          if (doc.getIsbn() != null) {
            bookDetails.setIsbn(doc.getIsbn().get(0));
          }
          bookDetails.setAuthor(doc.getAuthorNames());
          bookDetails.setCoverImageUrl(
              getCoverImageUrl(bookDetails.getIsbn() == null ? "" : bookDetails.getIsbn()));
          return bookDetails;
        })
        .collect(Collectors.toList());
  }

  private String getCoverImageUrl(String isbn) {
    return IMAGE_SEARCH_URL + isbn + "-M.jpg";
  }
}
