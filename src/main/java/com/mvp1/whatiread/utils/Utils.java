package com.mvp1.whatiread.utils;

import com.mvp1.whatiread.dto.AddressDTO;
import com.mvp1.whatiread.dto.AuthorDTO;
import com.mvp1.whatiread.dto.BookDTO;
import com.mvp1.whatiread.dto.ShelfDTO;
import com.mvp1.whatiread.dto.UserProfile;
import com.mvp1.whatiread.dto.UserSummary;
import com.mvp1.whatiread.entity.Author;
import com.mvp1.whatiread.entity.Book;
import com.mvp1.whatiread.entity.FriendShip;
import com.mvp1.whatiread.entity.Genre;
import com.mvp1.whatiread.entity.Shelf;
import com.mvp1.whatiread.entity.user.Address;
import com.mvp1.whatiread.entity.user.User;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor
public class Utils {

  // Constants
  public static final String USER_ROLE_NOT_SET = "User role not set";
  // External APIs
  public static final String IMAGE_SEARCH_URL = "https://covers.openlibrary.org/b/isbn/";
  // Internal API Paths
  public static final String AUTH_BASE_PATH = "/api/auth";
  public static final String SIGN_IN_PATH = "/signin";
  public static final String SIGN_UP_PATH = "/signup";
  // Model Mapper
  public static final ModelMapper modelMapper = new ModelMapper();
  private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

  // Initializer
  static {
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
  }

  // Converters
  public static AddressDTO convertAddressToAddressDto(Address address) {
    return AddressDTO.builder()
        .city(address.getCity())
        .street(address.getStreet())
        .suite(address.getSuite())
        .zipcode(address.getZipcode())
        .build();
  }

  public static Address convertAddressDTOToAddress(AddressDTO address) {
    return new Address(address.getStreet(), address.getSuite(), address.getCity(),
        address.getZipcode());
  }

  public static Set<UserProfile> convertFriendsToProfiles(Set<FriendShip> friends) {
    return friends.stream().map(user -> Utils.modelMapper.map(user.getFriend(), UserProfile.class))
        .collect(Collectors.toSet());
  }

  public static UserSummary convertUserToUserSummary(User user) {
    UserSummary userSummary = Utils.modelMapper.map(user, UserSummary.class);
    userSummary.setFriendsList(convertFriendsToProfiles(user.getFriends()));
    userSummary.setAddress(convertAddressToAddressDto(user.getAddress()));
    userSummary.setShelvesList(user.getShelves().stream()
        .map(Utils::convertShelfToShelfDTO).collect(Collectors.toSet()));
    return userSummary;
  }

  public static Set<Author> convertAuthorDTOToAuthors(Set<AuthorDTO> authors) {
    return authors.parallelStream().map(authorDTO ->
        modelMapper.map(authorDTO, Author.class)).collect(Collectors.toSet());
  }

  public static Set<Genre> convertBookGenreToGenres(Set<BookGenre> bookGenres) {
    return bookGenres.stream().map(genreDTO -> {
      Genre genre = new Genre();
      genre.setName(genreDTO.getDisplayName());
      genre.setDescription(genreDTO.getDescription());
      return genre;
    }).collect(Collectors.toSet());
  }

  public static Set<Book> convertBookDTOToBook(Set<BookDTO> bookDTO) {
    return bookDTO.stream().map(bookDto -> {
      Book book = modelMapper.map(bookDto, Book.class);
      book.setAuthors(convertAuthorDTOToAuthors(bookDto.getAuthorsList()));
      book.setGenres(convertBookGenreToGenres(bookDto.getGenresList()));
      return book;
    }).collect(Collectors.toSet());
  }

  public static Shelf convertShelfDTOToShelf(ShelfDTO shelfDTO) {
    Shelf shelf = modelMapper.map(shelfDTO, Shelf.class);
    shelf.setBooks(convertBookDTOToBook(shelfDTO.getBooksList()));
    return shelf;
  }

  public static Set<AuthorDTO> convertAuthorToAuthorDTO(Set<Author> authors) {
    return authors.stream().map(author -> AuthorDTO.builder()
        .name(author.getName())
        .biography(author.getBiography())
        .birthDate(author.getBirthDate())
        .deathDate(author.getDeathDate())
        .nationality(Nationality.valueOf(author.getNationality()))
        .build()).collect(Collectors.toSet());
  }

  public static Set<BookGenre> convertGenreToBookGenre(Set<Genre> genres) {
    return genres.stream().map(genre -> BookGenre.fromDisplayName(genre.getName()))
        .collect(Collectors.toSet());
  }

  public static Set<BookDTO> convertBookToBookDTO(Set<Book> books) {
    return books.stream().map(book -> {
      BookDTO bookDTO = modelMapper.map(book, BookDTO.class);
      bookDTO.setGenresList(convertGenreToBookGenre(book.getGenres()));
      bookDTO.setAuthorsList(convertAuthorToAuthorDTO(book.getAuthors()));
      return bookDTO;
    }).collect(Collectors.toSet());
  }

  public static ShelfDTO convertShelfToShelfDTO(Shelf shelf) {
    ShelfDTO shelfDTO = modelMapper.map(shelf, ShelfDTO.class);
    shelfDTO.setBooksList(convertBookToBookDTO(shelf.getBooks()));
    return shelfDTO;
  }
}
