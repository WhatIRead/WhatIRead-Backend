package com.mvp1.whatiread.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

@Data
@AllArgsConstructor
@Builder
public class UserSummary {

  @JsonIgnore
  private Long id;
  private String username;
  private String firstName;
  private String lastName;
}
