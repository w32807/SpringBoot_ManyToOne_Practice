package com.zerock.board.service;

import com.zerock.board.dto.BoardDTO;
import com.zerock.board.dto.PageRequestDto;
import com.zerock.board.dto.PageResultDto;
import com.zerock.board.entity.Board;
import com.zerock.board.entity.Member;
import com.zerock.board.repository.BoardRepository;
import com.zerock.board.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.function.Function;


@RequiredArgsConstructor
@Log4j2
@Service
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    @Override
    public Long register(BoardDTO dto) {
        return (boardRepository.save(dtoToEntity(dto))).getBno();
    }

    @Override
    public PageResultDto<BoardDTO, Object[]> getList(PageRequestDto pageRequestDto) {
        // DB에서 받아온 조인결과를 매핑시키는 Function 객체
        Function<Object[], BoardDTO> fn = (en -> entityToDTO((Board)en[0], (Member) en[1], (Long) en[2]));
        // 페이징을 위해서 DB에서 받아온 값을 Page 객체에 담아서 반환
        //Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageRequestDto.getPageable(Sort.by("bno").descending()));
        Page<Object[]> result = boardRepository.searchPage(pageRequestDto.getType(), pageRequestDto.getKeyword(), pageRequestDto.getPageable(Sort.by("bno").descending()));
        return new PageResultDto<>(result, fn);
    }

    @Override
    public BoardDTO get(Long bno) {
        Object[] arr = (Object[]) (boardRepository.getBoardByBno(bno));
        return entityToDTO((Board) arr[0], (Member) arr[1], (Long) arr[2]);
    }

    @Override
    @Transactional
    public void removeWithReplies(Long bno) {
        replyRepository.deleteByBno(bno);
        boardRepository.deleteById(bno);
    }

    @Override
    public void modify(BoardDTO boardDTO) {
        // findById와 getById는 로딩에 관한 차이가 있다. (객체간의 매핑관계에 따라서 달라짐)
        Board board = boardRepository.getById(boardDTO.getBno());
        if (board != null) {
            board.changeTitle(boardDTO.getTitle());
            board.changeContent(boardDTO.getContent());

            boardRepository.save(board);
        }
    }
}
