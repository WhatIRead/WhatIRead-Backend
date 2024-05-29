package com.mvp1.whatiread.entity;

import com.mvp1.whatiread.entity.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Shelf {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String description;
  private Boolean isPublic;
  private Date createdAt;
  @ManyToMany(mappedBy = "shelves")
  private Set<Book> books = new HashSet<>();
  @ManyToOne
  private User user;
}
