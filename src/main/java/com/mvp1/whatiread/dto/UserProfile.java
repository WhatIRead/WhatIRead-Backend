package com.mvp1.whatiread.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_EMPTY)
public class UserProfile {

  private Long id;
  @Schema(description = "Username of the user", example = "johndoe")
  private String username;

  @Schema(description = "First name of the user", example = "John")
  private String firstName;

  @Schema(description = "Last name of the user", example = "Doe")
  private String lastName;

  @Schema(description = "Date when the user joined", example = "2023-01-01T12:00:00Z")
  private Instant joinedAt;

  @Schema(description = "Email address of the user", example = "john.doe@example.com")
  private String email;

  @Schema(implementation = AddressDTO.class)
  private AddressDTO address;

  @Schema(description = "Phone number of the user", example = "+1234567890")
  private String phone;
}
