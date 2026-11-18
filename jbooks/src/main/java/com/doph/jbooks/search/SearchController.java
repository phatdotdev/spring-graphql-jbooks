package com.doph.jbooks.search;

import com.doph.jbooks.author.AuthorRepository;
import com.doph.jbooks.book.BookRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class SearchController {

    private static Logger log = LoggerFactory.getLogger(SearchController.class);
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @QueryMapping
    public List<Object> search(@Argument String text){
        log.info("Searching for {}", text);
        List<Object> result = new ArrayList<>();

        result.addAll(authorRepository.findAllByNameContainsIgnoreCase(text));
        result.addAll(bookRepository.findAllByTitleContainsIgnoreCase(text));

        return result;
    }
}
