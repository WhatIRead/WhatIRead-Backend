package com.mvp1.whatiread.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String isbn;
  @Column(nullable = false)
  private String title;
  private String subtitle;
  private String coverImage;
  private Double rating;
  @ManyToMany
  private Set<Author> authors = new HashSet<>();
  @ManyToMany
  private Set<Genre> genres = new HashSet<>();
  @ManyToMany
  private Set<Shelf> shelves = new HashSet<>();
}
