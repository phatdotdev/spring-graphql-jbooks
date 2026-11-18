package com.doph.jbooks.author;

import com.doph.jbooks.book.Book;
import com.doph.jbooks.book.BookRepository;
import com.doph.jbooks.search.SearchController;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class AuthorController {
    private static Logger log = LoggerFactory.getLogger(SearchController.class);
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @QueryMapping
    public List<Author> authors(){
        return authorRepository.findAll();
    }

    /*
    * An N+1 query problem happens when you execute one initial query
    * to fetch a list of $N$ records, and then, for each record,
    * you execute an additional query to fetch its related data.
    * */
    /*
    @SchemaMapping
    public List<Book> books(Author author) throws InterruptedException{
        log.info("Getting books for {}", author);
        Thread.sleep(1000);
        return new ArrayList<>();
    }
    */

    @BatchMapping
    public  List<List<Book>> books(List<Author> authors){
        log.info("Getting books for {}", authors.size());

        // get all of the authors
        List<Long> authorIds = authors
                .stream()
                .map(Author::getId)
                .toList();
        List<Book> allBooks = bookRepository.findByAuthorIdIn(authorIds);

        Map<Long, List<Book>> booksByAuthorId =  allBooks.stream()
                .collect(Collectors.groupingBy(book -> book.getAuthor().getId()));
        return authors
                .stream()
                .map(author ->
                        booksByAuthorId.getOrDefault(
                                author.getId(),
                                Collections.emptyList()))
                .toList();
    }


}
