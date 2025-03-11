package com.mvp1.whatiread.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.javafaker.Faker;
import com.mvp1.whatiread.dto.ShelfDTO;
import com.mvp1.whatiread.dto.UserSummary;
import com.mvp1.whatiread.entity.Book;
import com.mvp1.whatiread.entity.Shelf;
import com.mvp1.whatiread.exception.AccessDeniedException;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    when(shelfService.getAllShelvesForUser(userId)).thenReturn(new HashSet<>());
    ResponseEntity<Set<ShelfDTO>> response = shelfController.getAllShelvesForUser(currentUser);
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
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
      shelfController.getAllShelvesForUser(newUser);
    });
  }

  @Test
  void addShelf() {
    ShelfDTO shelfDTO = new ShelfDTO();
    shelfDTO.setName(faker.book().title());
    shelfDTO.setPublic(faker.random().nextBoolean());
    ResponseEntity<String> response = shelfController.addShelf(currentUser, shelfDTO);
    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    verify(shelfService, times(1)).addShelf(userId, shelfDTO);
  }

  @Test
  void getShelfForUser() {
    long shelfId = 1L;
    when(shelfService.getShelfForUser(userId, shelfId)).thenReturn(new ShelfDTO());
    ResponseEntity<ShelfDTO> response = shelfController.getShelfForUser(shelfId, currentUser);
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
  }

  @Test
  void updateShelf() {
    long shelfId = 1L;
    ShelfDTO shelfDTO = new ShelfDTO();
    shelfDTO.setName(faker.book().title());
    shelfDTO.setPublic(faker.random().nextBoolean());
    when(shelfService.getShelfForUser(userId, shelfId)).thenReturn(shelfDTO);
    ResponseEntity<String> response = shelfController.updateShelf(currentUser, shelfDTO, shelfId);
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    verify(shelfService, times(1)).getShelfForUser(userId, shelfId);
  }

  @Test
  void deleteShelf() {
    long shelfId = 1L;
    ResponseEntity<String> response = shelfController.updateShelf(currentUser, shelfId);
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    verify(shelfService, times(1)).deleteShelf(shelfId);
  }
}
