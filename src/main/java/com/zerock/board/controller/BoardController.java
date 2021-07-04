package com.zerock.board.controller;

import com.zerock.board.dto.BoardDTO;
import com.zerock.board.dto.PageRequestDto;
import com.zerock.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board/")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDto pageRequestDto, Model model){
        model.addAttribute("result", boardService.getList(pageRequestDto));
    }

    @GetMapping("/register")
    // 화면 전환용 메소드
    public void register(){}

    @PostMapping("/register")
    public String register(BoardDTO dto, RedirectAttributes rttr){
        Long bno = boardService.register(dto);
        rttr.addFlashAttribute("msg", bno);
        return "redirect:/board/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(@ModelAttribute("requestDTO") PageRequestDto pageRequestDto, Long bno, Model model){
        model.addAttribute("dto",boardService.get(bno));
    }

    @PostMapping("/remove")
    public String remove(long bno, RedirectAttributes rttr){
        boardService.removeWithReplies(bno);
        rttr.addFlashAttribute("msg", bno);
        return "redirect:/board/list";
    }

    @PostMapping("modify")
    public String modify(BoardDTO dto,@ModelAttribute("requestDTO") PageRequestDto requestDto, RedirectAttributes rttr){
        // 1. modify함수가 끝나면, 다시 read 메소드를 실행하고 싶다.
        // 2. 그러나, 1개의 http request에서 modify 메소드의 실행이 완료되면 view로 넘어간다.
        // 3. 바로 view로 넘어가면 조회하는 메소드를 modify에도 작성해 주어야 하기 때문에 중복이 발생한다.
        // 4. 그래서 데이터를 가지고 다시 read 메소드를 실행하고 싶다.
        // 5. RedirectAttributes의 addFlashAttribute 말고 addAttribute로 read 메소드 실행 시 필요한 데이터만 가지고 가자.
        boardService.modify(dto);
        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());
        // 이렇게 객체를 직접 전달해주면 안되고, view에서 read 메소드를 실행하는 것처럼 매개변수를 구성해야 함
        // rttr.addAttribute("requestDTO", requestDto);

        rttr.addAttribute("bno", dto.getBno());
        return "redirect:/board/read"; // redirect로 다시 실행할 메소드 지정 (redirect와 forward 차이 생각!)
    }
}

