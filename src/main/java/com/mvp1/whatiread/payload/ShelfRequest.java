package com.mvp1.whatiread.payload;

import java.util.List;
import lombok.Data;

@Data
public class ShelfRequest {

  private int id;
  private String name;
  private String description;
  private Boolean isPublic;
  private List<BookDTO> books;
}
