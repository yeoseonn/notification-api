package com.nfs.front.common;

import org.springframework.data.domain.Page;

import java.util.List;

public record ApiResults<T>(ApiHeader header, long totalCount, List<T> result) {
    public static <T> ApiResults<T> success(Page<T> results) {
        return new ApiResults<>(ApiHeader.successHeader(), results.getTotalElements(), results.getContent());
    }
}
