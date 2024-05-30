package com.mvp1.whatiread.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookDTO {

  private Long id;
  @NotBlank(message = "ISBN of book is required to save book information.")
  private String isbn;
  @NotBlank(message = "Title of book is required to save book information.")
  private String title;
  private String subtitle;
  private String coverImage;
  private Double rating;
}
