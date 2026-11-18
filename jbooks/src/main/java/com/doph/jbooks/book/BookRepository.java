package com.doph.jbooks.book;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByTitleContainsIgnoreCase(String text);

    List<Book> findByAuthorIdIn(List<Long> authorIds);
}
