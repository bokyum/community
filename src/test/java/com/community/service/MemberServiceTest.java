package com.community.service;

import com.community.domain.Member;
import com.community.dto.JoinMemberDto;
import com.community.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void 정상회원가입() {
        //given
        JoinMemberDto input = new JoinMemberDto("마르코12",
                "aser12@naver.com",
                "QHrua123!"
        );

        //when
        Long saveId = memberService.join(input);
        Member member = memberRepository.findById(saveId);

        //then
        assertThat(member.getUserName()).isEqualTo("마르코12");
        assertThat(member.getEmail()).isEqualTo("aser12@naver.com");
        assertThat(member.getPassword()).isEqualTo("QHrua123!");
    }
    @Test
    void 비정상가입() {
        JoinMemberDto joinMember = new JoinMemberDto("!@하이@", "123123@1qweq"
                , "qweqwe12");

        Assertions.assertThrows(IllegalArgumentException.class, ()->
        {
            memberService.join(joinMember);
        });
    }
}