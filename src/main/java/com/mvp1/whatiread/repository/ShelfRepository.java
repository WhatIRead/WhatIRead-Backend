package com.mvp1.whatiread.repository;

import com.mvp1.whatiread.entity.Book;
import com.mvp1.whatiread.entity.Shelf;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShelfRepository extends JpaRepository<Shelf, Long> {

  List<Shelf> findByUserId(Long userId);

  List<Book> findBooksById(long shelfId);
}
