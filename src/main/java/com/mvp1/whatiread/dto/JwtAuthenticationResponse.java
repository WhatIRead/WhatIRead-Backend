package com.mvp1.whatiread.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

@Data
@Schema(description = "JWT Authentication Response DTO")
public class JwtAuthenticationResponse {

  @Schema(description = "Username of the authenticated user", example = "johndoe")
  private String username;

  @Schema(description = "Roles assigned to the authenticated user", example = "[\"USER\", \"ADMIN\"]")
  private List<String> roles;

  @Schema(description = "JWT token issued for authentication")
  private String jwtToken;

  public JwtAuthenticationResponse(String username, List<String> roles, String jwtToken) {
    this.username = username;
    this.roles = roles;
    this.jwtToken = jwtToken;
  }
}
