package com.community.api.v1.service;


import com.community.api.v1.dto.members.CreateForm;
import com.community.api.v1.dto.members.LoginForm;
import com.community.api.v1.dto.members.MemberDetailForm;
import com.community.domain.Member;
import com.community.domain.repository.MemberRepository;
import com.community.domain.repository.SelectMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final SelectMemberRepository selectMemberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository, SelectMemberRepository selectMemberRepository) {
        this.memberRepository = memberRepository;
        this.selectMemberRepository = selectMemberRepository;
    }


    public boolean login(LoginForm loginForm) {
        //todo 서비스 다듬기
        selectMemberRepository.findByEmailAndPassword(loginForm);
    }

    public MemberDetailForm createMember(CreateForm form) {
        Member exist = memberRepository.findByEmail();
        if(exist != null)
            return null;

        Member member = new Member(form.getUsername(), form.getEmail(), form.getPassword());

    }
}
