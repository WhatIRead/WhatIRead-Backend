package com.mvp1.whatiread.repository;

import com.mvp1.whatiread.entity.user.User;
import com.mvp1.whatiread.exception.ResourceNotFoundException;
import com.mvp1.whatiread.security.UserPrincipal;
import jakarta.validation.constraints.NotBlank;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

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
