package com.community.service;

import com.community.domain.Member;
import com.community.dto.JoinMemberDto;
import com.community.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {


    private final MemberRepository memberRepository;


    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }



    //회원가입 후 아이디 반환
    @Transactional(readOnly = false)
    public Long join(JoinMemberDto joinMemberDto){
        Member member = new Member(
                joinMemberDto.getUserName(),
                joinMemberDto.getEmail(),
                joinMemberDto.getPassword()
        );

        try {
            memberRepository.save(member);

            return member.getId();
        }catch (Exception e) {
            throw new IllegalArgumentException(e.toString());
        }
    }
}
