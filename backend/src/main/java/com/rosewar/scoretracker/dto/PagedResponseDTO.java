package com.rosewar.scoretracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
public class PagedResponseDTO<T> {

    private List<T> content;       // 현재 페이지의 데이터 목록
    private int currentPage;       // 현재 페이지 번호
    private int pageSize;          // 페이지 크기
    private long totalElements;    // 전체 데이터 개수
    private int totalPages;        // 전체 페이지 수
    private boolean isLast;          // 마지막 페이지 여부

    // Spring Data JPA의 Page 객체를 사용하여 쉽게 PagedResponseDTO 생성
    public PagedResponseDTO(Page<T> page) {
        this.content = page.getContent();
        this.currentPage = page.getNumber();
        this.pageSize = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.isLast = page.isLast();
    }
}
