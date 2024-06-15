package com.mvp1.whatiread.payload;

import java.util.List;
import lombok.Data;

@Data
public class JwtAuthenticationResponse {

  private String username;
  private List<String> roles;
  private String jwtToken;

  public JwtAuthenticationResponse(String username, List<String> roles, String jwtToken) {
    this.username = username;
    this.roles = roles;
    this.jwtToken = jwtToken;
  }
}
