package com.doph.jbooks.reivew;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.graphql.data.GraphQlRepository;

import java.util.List;
import java.util.Map;

@GraphQlRepository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByBookIn(List<Long> bookIds);
}
