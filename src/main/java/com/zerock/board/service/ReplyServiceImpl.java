package com.zerock.board.service;

import com.zerock.board.dto.ReplyDTO;
import com.zerock.board.dto.PageRequestDto;
import com.zerock.board.dto.PageResultDto;
import com.zerock.board.entity.Board;
import com.zerock.board.entity.Member;
import com.zerock.board.entity.Reply;
import com.zerock.board.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Log4j2
@Service
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;

    @Override
    public Long register(ReplyDTO dto) {
        return (replyRepository.save(dtoToEntity(dto))).getRno();
    }

    @Override
    public List<ReplyDTO> getList(Long bno) {
        List<Reply> result = replyRepository.getRepliesByBoardOrderByRno(Board.builder().bno(bno).build());
        return result.stream().map(reply -> entityToDTO(reply)).collect(Collectors.toList());
    }

    @Override
    public void modify(ReplyDTO replyDTO) {
        replyRepository.save(dtoToEntity(replyDTO));
    }

    @Override
    public void remove(Long rno) {
        replyRepository.deleteById(rno);
    }
}
