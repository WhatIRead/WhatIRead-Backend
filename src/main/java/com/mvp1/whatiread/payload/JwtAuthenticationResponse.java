package com.mvp1.whatiread.payload;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {

  private String accessToken;
  private String tokenType = "Bearer";

  public JwtAuthenticationResponse(String accessToken) {
    this.accessToken = accessToken;
  }

}
