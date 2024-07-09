package com.mvp1.whatiread.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Schema(description = "Request DTO for user login")
public class LoginRequest {

  @NotBlank(message = "UserName or Email is required to login.")
  @Schema(description = "Username or email address of the user", example = "johndoe")
  private String usernameOrEmail;

  @NotBlank(message = "Password is required to login.")
  @Schema(description = "Password of the user", example = "password123")
  private String password;
}
