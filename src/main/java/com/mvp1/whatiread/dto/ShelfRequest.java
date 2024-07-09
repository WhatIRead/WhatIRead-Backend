package com.mvp1.whatiread.dto;

import java.util.List;
import lombok.Data;

@Data
public class ShelfRequest {
  private String name;
  private String description;
  private Boolean isPublic;
  private List<BookDTO> books;
}
