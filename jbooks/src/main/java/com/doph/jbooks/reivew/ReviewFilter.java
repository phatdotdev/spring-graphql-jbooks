package com.doph.jbooks.reivew;

public record ReviewFilter(
        boolean verified,
        int rating,
        String reviewerName
) {
}
