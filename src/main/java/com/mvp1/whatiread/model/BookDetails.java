package com.mvp1.whatiread.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDetails {

  private String title;
  private String isbn;
  private List<String> author;
  private String coverImageUrl;
}
