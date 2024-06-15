package com.mvp1.whatiread.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.github.javafaker.Faker;
import com.mvp1.whatiread.entity.Book;
import com.mvp1.whatiread.entity.Shelf;
import com.mvp1.whatiread.exception.AccessDeniedException;
import com.mvp1.whatiread.payload.UserSummary;
import com.mvp1.whatiread.repository.ShelfRepository;
import com.mvp1.whatiread.security.UserPrincipal;
import com.mvp1.whatiread.service.ShelfService;
import com.mvp1.whatiread.service.UserService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ShelfControllerTest {

  @Mock
  private ShelfRepository shelfRepository;
  @Mock
  private ShelfService shelfService;
  @Mock
  private UserService userService;
  @InjectMocks
  private ShelfController shelfController;
  private Faker faker;

  private UserPrincipal currentUser;
  private UserSummary userSummary;
  private List<Shelf> shelves;
  private long userId;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    faker = new Faker();
    userId = 1L;
    currentUser = UserPrincipal.builder().id(userId).firstName(faker.name().firstName())
        .lastName(faker.name().lastName()).email(faker.internet().emailAddress())
        .username(faker.name().username()).build();
    userSummary = UserSummary.builder().id(userId).firstName(faker.name().firstName())
        .lastName(faker.name()
            .lastName()).username(faker.name().username()).build();
    Set<Book> books = new HashSet<>();
    for (int i = 0; i < 10; ++i) {
      books.add(Book.builder().id(faker.random().nextLong()).isbn(faker.code().isbn10())
          .title(faker.book()
              .title()).build());
    }
    shelves = new ArrayList<>();
    for (int i = 0; i < 10; ++i) {
      shelves.add(Shelf.builder().id(faker.random().nextLong()).name(faker.book().title())
          .isPublic(faker.random()
              .nextBoolean()).books(books).build());
    }
  }

  @Test
  public void testGetAllShelvesForUser_Success() {
    when(userService.getCurrentUser(currentUser)).thenReturn(userSummary);
    when(shelfService.getAllShelvesForUser(userId)).thenReturn(shelves);

    List<Shelf> result = shelfController.getAllShelvesForUser(userId, currentUser);

    assertEquals(shelves, result);
  }

  @Test
  public void testGetAllShelvesForUser_AccessDenied() {
    long anotherUserId = 2L;
    UserPrincipal newUser = UserPrincipal.builder().id(anotherUserId)
        .firstName(faker.name().firstName())
        .lastName(faker.name().lastName()).email(faker.internet().emailAddress())
        .username(faker.name().username()).build();
    UserSummary anotherUser = new UserSummary(anotherUserId, faker.name().username(), faker.name()
        .firstName(), faker.name().lastName());
    when(userService.getCurrentUser(newUser)).thenReturn(anotherUser);

    assertThrows(AccessDeniedException.class, () -> {
      shelfController.getAllShelvesForUser(userId, newUser);
    });
  }

  @Test
  void addShelf() {
  }

  @Test
  void getShelfForUser() {
  }
}