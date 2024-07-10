package com.mvp1.whatiread.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mvp1.whatiread.utils.Nationality;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * DTO for {@link com.mvp1.whatiread.entity.Author}
 */
@AllArgsConstructor
@Getter
@Builder
@JsonInclude(Include.NON_EMPTY)
@Schema(description = "Data Transfer Object for Author information")
public class AuthorDTO implements Serializable {

  @NotBlank(message = "Author name is required.")
  @Schema(description = "Name of the author", example = "John Doe")
  private final String name;

  @Schema(description = "Biography of the author", example = "John Doe is a renowned writer known for his works in modern literature.")
  private final String biography;

  @Schema(description = "Nationality of the author", example = "AMERICAN")
  private final Nationality nationality;

  @Schema(description = "Birth date of the author", example = "1965-05-15")
  private final Date birthDate;

  @Schema(description = "Death date of the author", example = "2020-01-01")
  private final Date deathDate;

  @ArraySchema(arraySchema = @Schema(description = "Awards received by the author", example = "[\"Pulitzer Prize\", \"Nobel Prize in Literature\"]"))
  private final Set<String> awards;
}