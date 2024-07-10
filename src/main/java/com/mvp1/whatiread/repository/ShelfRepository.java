package com.mvp1.whatiread.repository;

import com.mvp1.whatiread.entity.Book;
import com.mvp1.whatiread.entity.Shelf;
import feign.Param;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShelfRepository extends JpaRepository<Shelf, Long> {

  Set<Shelf> findByUserId(Long userId);

  Set<Book> findBooksById(long shelfId);

  @Query(value = "SELECT s FROM Shelf s WHERE s.id = :shelfId AND s.user.id = :userId")
  Shelf findByIdAndUserId(@Param("userId") Long userId, @Param("shelfId") Long shelfId);
}
