package com.mvp1.whatiread.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginRequest {

  @NotBlank
  private String usernameOrEmail;

  @NotBlank
  private String password;
}
