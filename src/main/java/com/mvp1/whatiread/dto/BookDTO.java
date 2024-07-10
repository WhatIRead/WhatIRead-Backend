package com.mvp1.whatiread.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mvp1.whatiread.utils.BookGenre;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Data Transfer Object for Book information")
public class BookDTO {

  @NotNull(message = "Book ISBN number should not be null")
  @Schema(description = "International Standard Book Number", example = "978-3-16-148410-0")
  private String isbn;

  @NotNull(message = "Book Title should not be null")
  @Schema(description = "Title of the book", example = "Effective Java")
  private String title;

  @Schema(description = "Subtitle of the book", example = "A Comprehensive Guide to Best Practices")
  private String subtitle;
  @Schema(description = "URL to the cover image of the book", example = "https://example.com/images/effective-java.jpg")
  private String coverImage;

  @Range(min = 0, max = 10, message = "Please provide a rating between 0 and 10")
  @Schema(description = "Rating of the book", example = "4.5")
  private Double rating;

  @ArraySchema(arraySchema = @Schema(description = "List of authors of the book"), schema = @Schema(implementation = AuthorDTO.class))
  private Set<AuthorDTO> authorsList;

  @ArraySchema(arraySchema = @Schema(description = "List of genres the book belongs to", example = "[\"SCIENCE_FICTION\", \"ADVENTURE\"]"))
  private Set<BookGenre> genresList;
}