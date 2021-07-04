package com.zerock.board.reply.repository;

import com.zerock.board.entity.Board;
import com.zerock.board.entity.Member;
import com.zerock.board.entity.Reply;
import com.zerock.board.repository.ReplyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertReplyTest(){
        IntStream.rangeClosed(1, 100).forEach(i -> {
            long bno = (long)(Math.random() * 100) + 1;
            Member member = Member.builder().email("user"+i+"@gmail.com").build();
            // FK를 넣을 때는 객체로써 넣어주어야 한다.
            Board board = Board.builder().bno(bno).title("Title"+i).content("Content"+i).writer(member).build();

            Reply reply = Reply.builder().text("Reply" + i).board(board).replyer("guest").build();
            replyRepository.save(reply);
        });
    }

    @Test
    public void testListByBoard(){
        List<Reply> replyList = replyRepository.getRepliesByBoardOrderByRno(Board.builder().bno(97L).build());
        replyList.forEach(reply -> System.out.println(reply));
    }
}
