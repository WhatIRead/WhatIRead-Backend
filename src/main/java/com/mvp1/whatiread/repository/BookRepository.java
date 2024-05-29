package com.mvp1.whatiread.repository;

import com.mvp1.whatiread.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {

}
