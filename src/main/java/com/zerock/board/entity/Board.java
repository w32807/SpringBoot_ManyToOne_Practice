package com.zerock.board.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer")
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long bno;

    private String title;

    private String content;
    
    @ManyToOne(fetch = FetchType.LAZY)
    // Member 1명당 여러 개의 board 작성 가능하므로, 1:N일 때 N인 쪽에 @ManyToOne를 선언해줌
    private Member writer;

    // 제목, 내용만 수정하기 위한 메소드
    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }
}
