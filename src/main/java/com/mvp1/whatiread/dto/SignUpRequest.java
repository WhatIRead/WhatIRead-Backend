package com.mvp1.whatiread.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
@JsonInclude(Include.NON_EMPTY)
@Schema(description = "Request DTO for user sign up")
public class SignUpRequest {

  @NotBlank
  @Size(min = 4, max = 40)
  @Schema(description = "First name of the user", example = "John")
  private String firstName;

  @NotBlank
  @Size(min = 4, max = 40)
  @Schema(description = "Last name of the user", example = "Doe")
  private String lastName;

  @NotBlank
  @Size(min = 3, max = 15)
  @Schema(description = "Username for the user", example = "johndoe")
  private String username;

  @NotBlank
  @Size(max = 40)
  @Email
  @Schema(description = "Email address of the user", example = "john.doe@example.com")
  private String email;

  @Schema(description = "Phone number of the user", example = "+1234567890")
  private String phone;

  @NotBlank
  @Size(min = 6, max = 20)
  @Schema(description = "Password for the user", example = "password123")
  private String password;

  @Schema(implementation = AddressDTO.class)
  private AddressDTO address;
}
