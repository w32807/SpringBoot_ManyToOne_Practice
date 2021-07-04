package com.zerock.board.repository;

import com.zerock.board.entity.Board;
import com.zerock.board.repository.search.SearchBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> , SearchBoardRepository {
    // FK인 Member와의 조인을 위해, 새로운 메소드 선언
    // 쿼리는, Board 1개에서 꺼내온 b.writer와 조인을 하는 것임
    // 내부에 있는 엔티티를 이용한 조인에서는 ON절이 없음을 주의 (만약 엔티티간의 관계가 없다면, 명시적으로 on절을 작성해줘야 함)
    // select 할것 + 기준 엔티티 + left join + (조인할 엔티티, on절 생략 및 연결되는 필드) + where 절
    // 결과가 1개이므로 Object 반환 값
    @Query("select b, w from Board b left join b.writer w where b.bno = :bno")
    Object getBoardWithWriter(@Param("bno") Long bno);

    @Query("select b, r from Board b left join Reply r ON r.board = b where b.bno = :bno")
    // 결과가 리스트이므로, Object[] 반환 값
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);

    @Query(value = "SELECT b, w, count(r) " +
            " FROM Board b " +
            " LEFT JOIN b.writer w " +
            " LEFT JOIN Reply r ON r.board = b " +
            " GROUP BY b", countQuery = "SELECT count(b) FROM Board b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);

    @Query("SELECT b, w, count(r) " +
            " FROM Board b LEFT JOIN b.writer w " +
            " LEFT OUTER JOIN Reply r ON r.board = b " +
            " WHERE b.bno = :bno")
    Object getBoardByBno(@Param("bno") Long bno);
}
