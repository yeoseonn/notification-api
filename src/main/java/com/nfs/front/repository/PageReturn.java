package com.nfs.front.repository;

import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;

import java.util.Collections;
import java.util.List;

public interface PageReturn<T> {
    default Page<T> queryToPage(JPQLQuery<T> query, Pageable pageable, Querydsl querydsl) {
        long count = query.fetchCount();
        if (count == 0) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0L);
        }
        List<T> content = querydsl.applyPagination(pageable, query).fetch();
        return new PageImpl<>(content, pageable, count);
    }
}
