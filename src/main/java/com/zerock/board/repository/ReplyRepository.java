package com.zerock.board.repository;

import com.zerock.board.entity.Board;
import com.zerock.board.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    @Modifying // JPQL을 이용해 delete, update를 할 때는 @Modifying 어노테이션을 선언해 주어야 한다.
    // 다건의 UPDATE, DELETE 연산을 하나의 쿼리로 할 때 사용됨
    @Query("delete from Reply r where r.board.bno = :bno")
    void deleteByBno(Long bno);

    // 게시물로 댓글 목록 가져오기
    List<Reply> getRepliesByBoardOrderByRno(Board board);


}
