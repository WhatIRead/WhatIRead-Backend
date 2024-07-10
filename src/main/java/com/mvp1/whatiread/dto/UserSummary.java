package com.mvp1.whatiread.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mvp1.whatiread.entity.Shelf;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(Include.NON_EMPTY)
public class UserSummary {

  private Long id;

  @NotBlank
  @Schema(description = "Username of the user", example = "johndoe")
  private String username;

  @NotBlank
  @Email
  private String email;

  @NotBlank
  @Schema(description = "First name of the user", example = "John")
  private String firstName;

  @NotBlank
  @Schema(description = "Last name of the user", example = "Doe")
  private String lastName;

  @Schema(description = "Phone number of the user", example = "+1234567890")
  private String phone;

  @Schema(description = "Address of the user")
  private AddressDTO address;

  @ArraySchema(schema = @Schema(description = "Set of shelves belonging to the user", implementation = Shelf.class))
  private Set<ShelfDTO> shelvesList;

  @ArraySchema(schema = @Schema(description = "Set of user profiles representing friends", implementation = UserProfile.class))
  private Set<UserProfile> friendsList;
}
