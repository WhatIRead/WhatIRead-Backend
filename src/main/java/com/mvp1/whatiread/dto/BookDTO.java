package com.mvp1.whatiread.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookDTO {

  @NotNull(message = "Book ISBN number should not be null")
  private String isbn;
  @NotNull(message = "Book Title should not be null")
  private String bookName;
  private String author;
  private String tagLine;
}

