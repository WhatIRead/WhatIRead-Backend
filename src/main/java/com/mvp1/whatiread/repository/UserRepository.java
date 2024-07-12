package com.mvp1.whatiread.repository;

import com.mvp1.whatiread.entity.user.User;
import com.mvp1.whatiread.exception.ResourceNotFoundException;
import com.mvp1.whatiread.security.UserPrincipal;
import jakarta.validation.constraints.NotBlank;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

  @EntityGraph(attributePaths = {"address", "friends", "shelves"})
  Optional<User> findUserWithDetailsById(Long id);

  @Query("SELECT u FROM User u WHERE u.username LIKE :username%")
  Set<User> findUsersLikeUsername(@Param("username") String username);


  Optional<User> findByUsername(@NotBlank String username);

  Optional<User> findByEmail(@NotBlank String email);

  Boolean existsByUsername(@NotBlank String username);

  Boolean existsByEmail(@NotBlank String email);

  Optional<User> findByUsernameOrEmail(String username, String email);

  default User getUser(UserPrincipal currentUser) {
    return getUserByName(currentUser.getUsername());
  }

  default User getUserByName(String username) {
    return findByUsername(username)
        .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
  }
}
