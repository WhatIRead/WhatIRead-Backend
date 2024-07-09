package com.mvp1.whatiread.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Getter;

@Getter
@Schema(description = "Enum representing different nationalities")
public enum Nationality implements Serializable {

  @Schema(description = "American nationality")
  AMERICAN("American"),

  @Schema(description = "British nationality")
  BRITISH("British"),

  @Schema(description = "Canadian nationality")
  CANADIAN("Canadian"),

  @Schema(description = "French nationality")
  FRENCH("French"),

  @Schema(description = "German nationality")
  GERMAN("German"),

  @Schema(description = "Indian nationality")
  INDIAN("Indian"),

  @Schema(description = "Japanese nationality")
  JAPANESE("Japanese"),

  @Schema(description = "Chinese nationality")
  CHINESE("Chinese"),

  @Schema(description = "Australian nationality")
  AUSTRALIAN("Australian"),

  @Schema(description = "Other nationality")
  OTHER("Other");

  private final String displayName;

  Nationality(String displayName) {
    this.displayName = displayName;
  }
}
