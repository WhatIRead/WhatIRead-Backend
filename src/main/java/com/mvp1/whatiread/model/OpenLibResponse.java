package com.mvp1.whatiread.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OpenLibResponse {

  @JsonProperty("docs")
  private List<Document> docs;
}

