package com.mvp1.whatiread.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
@Schema(description = "Enum representing different book genres")
public enum BookGenre implements Serializable {
  // Fiction Genres
  ADVENTURE("Adventure",
      "Stories of journeys and quests often involving danger or exciting experiences."),
  CLASSIC("Classic",
      "Beloved works that have stood the test of time and are considered significant in literary history."),
  HISTORICAL_FICTION("Historical Fiction",
      "Stories set in the past, often featuring real historical events or figures."),
  LITERARY_FICTION("Literary Fiction",
      "Character-driven stories focusing on themes, style, and depth of characterization."),
  MYSTERY("Mystery",
      "Plots centered around solving a crime or puzzle, often with a detective or amateur sleuth."),
  ROMANCE("Romance",
      "Stories of love and relationships, typically with a focus on emotional and romantic development."),
  SCIENCE_FICTION("Science Fiction",
      "Speculative fiction exploring futuristic or scientific concepts, often set in space or alternate realities."),
  FANTASY("Fantasy",
      "Stories featuring magical or supernatural elements, often set in imaginary worlds or with mythical creatures."),
  THRILLER("Thriller",
      "Suspenseful stories with high stakes and intense pacing, often involving danger or crime."),

  // Non-Fiction Genres
  BIOGRAPHY("Biography", "Accounts of a person's life, written by another person."),
  AUTOBIOGRAPHY("Autobiography", "An account of a person's life written by themselves."),
  MEMOIR("Memoir", "Personal accounts of specific events or periods in a person's life."),
  HISTORY("History", "Academic or narrative accounts of past events, societies, or cultures."),
  SELF_HELP("Self-Help",
      "Books offering advice or guidance on personal improvement, mental health, or achieving goals."),
  TRAVEL("Travel",
      "Accounts of travel experiences, often including descriptions of places visited and cultural insights."),
  COOKBOOK("Cookbook", "Collections of recipes and culinary tips."),
  ESSAY("Essay",
      "Short literary pieces discussing various topics, often personal or opinionated in nature."),
  TRUE_CRIME("True Crime", "Accounts of real-life crimes, investigations, and legal proceedings."),

  // Other Genres
  POETRY("Poetry", "Literary works characterized by rhythm, imagery, and often meter or rhyme."),
  GRAPHIC_NOVEL("Graphic Novel",
      "Narrative works presented in comic-strip format, often with more complex storytelling and themes."),
  YOUNG_ADULT("Young Adult (YA)",
      "Books targeted at readers aged roughly 12-18, covering a wide range of genres but often focusing on coming-of-age themes and issues.");
  private static final Map<String, BookGenre> DISPLAY_NAME_MAP = new HashMap<>();

  static {
    for (BookGenre genre : BookGenre.values()) {
      DISPLAY_NAME_MAP.put(genre.displayName, genre);
    }
  }

  private final String displayName;
  private final String description;

  BookGenre(String displayName, String description) {
    this.displayName = displayName;
    this.description = description;
  }

  public static BookGenre fromDisplayName(String displayName) {
    return DISPLAY_NAME_MAP.get(displayName);
  }
}

