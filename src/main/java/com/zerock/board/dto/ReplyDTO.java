package com.zerock.board.dto;

import lombok.*;

import java.time.LocalDateTime;
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {
    private Long rno;
    private String text;
    private String replyer;
    private Long bno;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}

