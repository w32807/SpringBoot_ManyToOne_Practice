package com.zerock.board.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDto<DTO, EN> {
    private List<DTO> dtoList;

    // 총 페이지 번호
    private int totalPage;

    // 현재 페이지 번호
    private int page;

    // 목록 사이즈
    private int size;

    // 시작 페이지번호, 끝 페이지번호
    private int start, end;

    // 이전 다음 여부
    private boolean prev, next;

    // 페이지 번호 목록
    private List<Integer> pageList;

    // result : DAO에서 받아온 Page<Entity> List
    // fn : result를 변환 할 Fuction
    public PageResultDto(Page<EN> result, Function<EN, DTO> fn) {
        // DAO에서 가져온 Page<EN>와 사용자 정의 함수를 매개변수로 받아서, 함수처리를 한 List를 반환
        // 즉 Entity List를 DTO List로 변환하는 역할
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        // Page 인터페이스에서 제공하는 전체 페이지 갯수 가져오기
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {
        this.page = pageable.getPageNumber() + 1; // 0부터 시작하므로 1 증가
        this.size = pageable.getPageSize();

        // temp end Page
        int tmpEnd = (int)(Math.ceil(page/10.0)) * 10;
        start = tmpEnd - 9;
        prev = start > 1;
        end = totalPage > tmpEnd ? tmpEnd : totalPage;
        next = totalPage > tmpEnd;

        // 화면에 보여줄 페이지번호를 Integer 타입으로 boxing.
        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }
}