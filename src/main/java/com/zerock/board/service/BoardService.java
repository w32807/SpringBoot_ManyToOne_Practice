package com.zerock.board.service;


import com.zerock.board.dto.BoardDTO;
import com.zerock.board.dto.PageRequestDto;
import com.zerock.board.dto.PageResultDto;
import com.zerock.board.entity.Board;
import com.zerock.board.entity.Member;

public interface BoardService {
    Long register(BoardDTO dto);
    // 함수를 Object[] 취급
    PageResultDto<BoardDTO, Object[]> getList(PageRequestDto pageRequestDto);

    BoardDTO get(Long bno);

    void removeWithReplies(Long bno);

    void modify(BoardDTO boardDTO);

    default Board dtoToEntity(BoardDTO dto){
        // Board를 가지고 작업하기 전 외래키인 Member Entity 처리를 먼저 해 준다.
        Member member = Member.builder().email(dto.getWriterEmail()).build();
        // 외래키인 객체까지 넣어서 Build
        Board board = Board.builder().bno(dto.getBno()).title(dto.getTitle()).content(dto.getContent()).writer(member).build();

        return board;
    }

    default BoardDTO entityToDTO(Board board, Member member, Long replyCount){
        BoardDTO boardDTO = BoardDTO.builder()
                            .bno(board.getBno())
                            .title(board.getTitle())
                            .content(board.getContent())
                            .regDate(board.getRegDate())
                            .modDate(board.getModDate())
                            .writerEmail(member.getEmail())
                            .writerName(member.getName())
                            .replyCount(replyCount.intValue())
                            .build();
        return boardDTO;
    }
}
