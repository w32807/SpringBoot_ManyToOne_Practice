package com.zerock.board.controller;

import com.zerock.board.dto.BoardDTO;
import com.zerock.board.dto.PageRequestDto;
import com.zerock.board.dto.ReplyDTO;
import com.zerock.board.service.BoardService;
import com.zerock.board.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RestController
@RequestMapping("/replies/")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    // ResponseEntity란 상태값과, 데이터를 같이 묶어서 보낼 수 있는 객체임 (제네릭으로 선언)

    @GetMapping(value = "/board/{bno}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReplyDTO>> getListByBoard(@PathVariable("bno") Long bno){
        return new ResponseEntity<>(replyService.getList(bno), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Long> register(@RequestBody ReplyDTO replyDTO){
        return new ResponseEntity<>(replyService.register(replyDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{rno}")
    public ResponseEntity<String> remove(@PathVariable("rno") Long rno){
        replyService.remove(rno);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PutMapping("/{rno}")
    public ResponseEntity<String> modify(@RequestBody ReplyDTO replyDTO){
        replyService.modify(replyDTO);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}

