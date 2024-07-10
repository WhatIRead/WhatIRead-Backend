package com.mvp1.whatiread.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for {@link com.mvp1.whatiread.entity.user.Address}
 */
@AllArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "Data Transfer Object for Address information")
public class AddressDTO implements Serializable {

  @NotBlank(message = "Street is required.")
  @Schema(description = "Street name of the address", example = "123 Main St")
  private final String street;

  @NotBlank(message = "Suite is required.")
  @Schema(description = "Suite number of the address", example = "Apt 4B")
  private final String suite;

  @NotBlank(message = "City is required.")
  @Schema(description = "City of the address", example = "Springfield")
  private final String city;

  @NotBlank(message = "Zipcode is required.")
  @Schema(description = "Zipcode of the address", example = "12345")
  private final String zipcode;
}