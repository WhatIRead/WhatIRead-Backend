package com.mvp1.whatiread.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request DTO for creating or updating a book shelf")
public class ShelfDTO {

  @Schema(description = "Name of the shelf", example = "My Favorite Books")
  private String name;

  @Schema(description = "Description of the shelf", example = "A collection of my favorite books of all time")
  private String description;

  @Schema(description = "Visibility of the shelf", example = "true")
  private Boolean isPublic;

  @ArraySchema(arraySchema = @Schema(description = "List of books added to the shelf"), schema = @Schema(implementation = BookDTO.class))
  private Set<BookDTO> booksList;
}
