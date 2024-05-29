package com.mvp1.whatiread.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Document {

  @JsonProperty("title")
  private String title;
  @JsonProperty("isbn")
  private List<String> isbn;
  @JsonProperty("author_name")
  private List<String> authorNames;
}
