package com.community.service;

import com.community.domain.Member;
import com.community.dto.RequestMemberDto;
import com.community.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.NoSuchObjectException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public Long join(RequestMemberDto requestMemberDto){
        Member member = new Member(
                requestMemberDto.getName(),
                requestMemberDto.getEmail(),
                requestMemberDto.getPassword()
        );
        memberRepository.save(member);
        return member.getId();

    }

    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    public Member findMemberById(Long id) {
        Member member = memberRepository.findById(id);
        return member;
    }

    public List<Member> findMemberByName(String name) {
        List<Member> members = memberRepository.findByName(name);
        return members;
    }

    @Transactional(readOnly = false)
    public Member updateMemberById(Long id, RequestMemberDto requestMemberDto) {
        return memberRepository.updateById(id, requestMemberDto);
    }

    @Transactional(readOnly = false)
    public void deleteMemberById(Long id) {
        memberRepository.deleteById(id);
    }

    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Transactional(readOnly = false)
    public void deleteMember(Member member) {
        memberRepository.deleteMember(member);
    }


}
