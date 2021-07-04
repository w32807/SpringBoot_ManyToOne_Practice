package com.zerock.board.reply.service;

import com.zerock.board.dto.ReplyDTO;
import com.zerock.board.service.ReplyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ReplyServiceTest {

    @Autowired
    ReplyService replyService;

    @Test
    public void testGetList(){
        List<ReplyDTO> replyDTOList = replyService.getList(100L);
        replyDTOList.forEach(replyDTO -> System.out.println(replyDTO));
    }
}
