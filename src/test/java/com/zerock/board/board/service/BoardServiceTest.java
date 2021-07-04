package com.zerock.board.board.service;

import com.zerock.board.dto.BoardDTO;
import com.zerock.board.dto.PageRequestDto;
import com.zerock.board.dto.PageResultDto;
import com.zerock.board.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardServiceTest {

    @Autowired
    BoardService boardService;

    @Test
    public void testRegister(){
        BoardDTO dto = BoardDTO.builder().title("Test Title").content("Test,,,,").content("Test...").writerEmail("user55@gmail.com").build();
    }

    @Test
    public void testList(){
        PageRequestDto pageRequestDto = new PageRequestDto();
        PageResultDto<BoardDTO, Object[]> result = boardService.getList(pageRequestDto);
        for (BoardDTO boardDTO : result.getDtoList()){
            System.out.println(boardDTO);
        }
    }

    @Test
    public void testGet(){
        System.out.println(boardService.get(100L));
    }
}
