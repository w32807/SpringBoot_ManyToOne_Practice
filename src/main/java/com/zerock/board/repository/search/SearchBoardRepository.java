package com.zerock.board.repository.search;

import com.zerock.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// 1. 쿼리 메소드나 @Query로 처리할 수 없는 쿼리는 별도의 인터페이스로 설계 (Mybatis를 사용해도 된다)
// 2. Querydsl을 사용하기 위해서는 QuerydslRepositorySupport 클래스를 부모클래스로 사용.
public interface SearchBoardRepository {
    // 결과로 받는 데이터가 테이블의 여러 row들이므로 반환형을 Page<Object[]>로 작성
    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);
}
