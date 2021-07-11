package com.community.service;

import com.community.domain.Member;
import com.community.dto.RequestMemberDto;
import com.community.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";


    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }



    //회원가입 후 아이디 반환
    @Transactional(readOnly = false)
    public Long join(RequestMemberDto requestMemberDto){
        try {
            isValidName(requestMemberDto.getName());
            isValidPassword(requestMemberDto.getPassword());
            isValidEmail(requestMemberDto.getEmail());
        } catch (Exception e){
            throw e;
        }
        Member member = new Member(
                requestMemberDto.getName(),
                requestMemberDto.getEmail(),
                requestMemberDto.getPassword()
        );

        try {
            memberRepository.save(member);

            return member.getId();
        }catch (Exception e) {
            throw new IllegalArgumentException("이미 존재하는 email 입니다");
        }
    }

    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    public Member findMemberById(Long id) {
        Member member = memberRepository.findById(id);
        if(member == null)
            throw new IllegalArgumentException("존재하지 않는 아이디 입니다.");
        return member;
    }

    public List<Member> findMemberByName(String name) {
        List<Member> members = memberRepository.findByName(name);
        if(members.size()==0)
            throw new IllegalArgumentException("존재하지 않는 이름 입니다.");
        return members;
    }

    private void isValidPassword(String password) {
        if(password.length() <= 8)
            throw new IllegalArgumentException("8글자 이상의 비밀번호를 입력하세요");
        if(password.length() > 50)
            throw new IllegalArgumentException("20글자 이하의 비밀번호를 입력하세요");

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        if(!matcher.matches())
            throw new IllegalArgumentException("잘못된 비밀번호 형식입니다.");
    }

    private void isValidName(String name) {
        if(name.length() > 20)
            throw new IllegalArgumentException("입력한 이름의 길이가 너무 깁니다.");
        if(!Pattern.matches("^[0-9a-zA-Z가-힣]*$", name))
            throw new IllegalArgumentException("이름의 특수문자는 들어갈 수 없습니다.");
    }

    private void isValidEmail(String email) {
        if(email.length() > 30 || email.length() < 10)
            throw new IllegalArgumentException("Email의 길이는 10 ~ 20의 값을 가져야합니다.");
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches())
            throw new IllegalArgumentException("잘못된 이메일 형식입니다 ");
    }
}
