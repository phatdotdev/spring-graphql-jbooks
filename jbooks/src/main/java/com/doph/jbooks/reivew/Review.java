package com.doph.jbooks.reivew;

import com.doph.jbooks.book.Book;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reviewerName;
    private Integer rating;
    private String comment;

    private LocalDateTime createdAt;
    private Boolean verified;

    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;
}
