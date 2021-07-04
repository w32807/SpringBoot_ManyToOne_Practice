package com.zerock.board.board.repository;

import com.zerock.board.entity.Board;
import com.zerock.board.entity.Member;
import com.zerock.board.repository.BoardRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @Disabled
    public void insertBoardTest(){
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder().email("user"+i+"@gmail.com").build();
            // FK를 넣을 때는 객체로써 넣어주어야 한다.
            Board board = Board.builder().title("Title"+i).content("Content"+i).writer(member).build();
            boardRepository.save(board);
        });
    }

    @Test
    @Transactional
    public void testRead1(){
        // Board만 조회하고 싶은데 Member가 외래키로 잡혀있어서 항상 Join한 결과를 가져오게 된다.
        Optional<Board> result = boardRepository.findById(100L);
        Board board = result.get();

        System.out.println(board);
        // 지연 로딩으로 Board만 가져오는데 getWriter를 호출하면 DB session이 끊긴 상태에서 추가 DB 호출을 하므로 오류!
        // 그래서 @Transactional를 꼭 사용해야 한다. (즉 무조건 조인을 하는게 아니라 필요에 따라 추가 DB 쿼리 호출을 한다)
        System.out.println(board.getWriter());
    }

    @Test
    public void testReadWithWriter(){
        Object result = boardRepository.getBoardWithWriter(100L);
        // 결과가 Entity 단위의 배열로 넘어온다. 그러므로 Object[]로 형변환함
        Object arr[] = (Object[]) result;
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testGetBoardWithReply(){
        List<Object[]> result = boardRepository.getBoardWithReply(100L);

        for (Object[] arr : result){
            System.out.println(Arrays.toString(arr));
        }
    }

    @Test
    public void testWithReplyCount(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);

        result.get().forEach(row -> {
            Object[] arr = (Object[])row;
            System.out.println(Arrays.toString(arr));
        });
    }

    @Test
    public void testRead(){
        Object result = boardRepository.getBoardByBno(100L);
        System.out.println(Arrays.toString((Object[]) result));
    }

    @Test
    public void testSearchPage(){
        Pageable pageable = PageRequest.of(0,10,Sort.by("bno").descending());
        Page<Object[]> result = boardRepository.searchPage("t", "1", pageable);
    }
}
