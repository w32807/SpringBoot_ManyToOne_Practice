package com.zerock.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDto {
    private int page;
    private int size;
    private String type; // 검색 타입 (제목, 제목 + 내용 등등..)
    private String keyword; // 검색어

    public PageRequestDto() {
        this.page = 1;
        this.size = 10;
    }

    public Pageable getPageable(Sort sort) {
        return PageRequest.of(page - 1, size, sort);
    }
}