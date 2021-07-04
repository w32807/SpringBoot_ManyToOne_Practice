package com.zerock.board.member.repository;

import com.zerock.board.entity.Member;
import com.zerock.board.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertMemberTest(){
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder().email("user"+i+"@gmail.com").password("111").name("User"+i).build();
            memberRepository.save(member);
        });
    }
}
