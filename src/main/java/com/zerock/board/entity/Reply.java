package com.zerock.board.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "board")
public class Reply extends BaseEntity{

    @Id
    // Oracle은 Identity가 아닌 SEQUENCE로 해야 함
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long rno;

    private String text;

    private String replyer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;
}
