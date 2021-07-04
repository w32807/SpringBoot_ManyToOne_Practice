package com.zerock.board.service;


import com.zerock.board.dto.ReplyDTO;
import com.zerock.board.dto.PageRequestDto;
import com.zerock.board.dto.PageResultDto;
import com.zerock.board.entity.Board;
import com.zerock.board.entity.Member;
import com.zerock.board.entity.Reply;

import java.util.List;

public interface ReplyService {
    Long register(ReplyDTO replyDTO);

    List<ReplyDTO> getList(Long bno);

    void modify(ReplyDTO ReplyDTO);

    void remove(Long rno);


    default Reply dtoToEntity(ReplyDTO replyDTO){
        Board board = Board.builder().bno(replyDTO.getBno()).build();
        return Reply.builder().rno(replyDTO.getRno()).text(replyDTO.getText()).replyer(replyDTO.getReplyer()).board(board).build();
    }

    default ReplyDTO entityToDTO(Reply reply){
        return ReplyDTO.builder().rno(reply.getRno()).text(reply.getText()).replyer(reply.getReplyer())
                .regDate(reply.getRegDate()).modDate(reply.getModDate()).build();
    }
}
