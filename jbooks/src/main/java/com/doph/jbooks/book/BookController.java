package com.doph.jbooks.book;

import com.doph.jbooks.author.Author;
import com.doph.jbooks.author.AuthorRepository;
import com.doph.jbooks.reivew.Review;
import com.doph.jbooks.reivew.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BookController {
    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ReviewRepository reviewRepository;

    @QueryMapping
    public List<Book> books(){
        return bookRepository.findAll();
    }

    @QueryMapping
    public Optional<Book> book(@Argument Long id){
        return bookRepository.findById(id);
    }

    @MutationMapping
    public Book addBook(@Argument BookInput bookInput){
        Author author = authorRepository
                .findById(bookInput.authorId()).orElseThrow(

                );
        var book = new Book();
        book.setTitle(bookInput.title());
        book.setAuthor(author);
        return bookRepository.save(book);
    }

    @BatchMapping
    public List<List<Review>> reviews(List<Book> books){
        List<Long> bookIds = books
                .stream()
                .map(book -> book.getId())
                .toList();
        Map<Long, List<Review>> reviewsByBookId = reviewRepository.findByBookIn(bookIds)
                .stream().collect(Collectors.groupingBy(review -> review.getBook().getId()));
        return books.stream()
                .map(
                book -> reviewsByBookId.getOrDefault(book.getId(), Collections.emptyList()))
                .toList();
    }
}
