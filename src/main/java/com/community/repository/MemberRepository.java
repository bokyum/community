package com.community.repository;

import com.community.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
@Slf4j
public class MemberRepository {


    private final EntityManager em;
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

    @Autowired
    public MemberRepository(EntityManager em) {

        this.em = em;
    }

    public void save(Member member) {
        try {
            isValidEmail(member);
            isValidName(member.getUserName());
            isValidPassword(member);

            em.persist(member);
        }
        catch (Exception e) {
            log.info(e.toString());
            throw new IllegalArgumentException(e.toString());
        }
    }


    public Member findById(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String userName) {
        return em.createQuery("select m from Member m where m.userName =: userName", Member.class)
                .getResultList();

    }


    private void isValidPassword(Member member) {
        String password = member.getPassword();
        if(password.length() <= 8)
            throw new IllegalArgumentException("8글자 이상의 비밀번호를 입력하세요");
        if(password.length() > 50)
            throw new IllegalArgumentException("20글자 이하의 비밀번호를 입력하세요");

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        if(!matcher.matches())
            throw new IllegalArgumentException("잘못된 비밀번호 형식입니다. " + password);
    }

    private void isValidName(String userName) {
        if(userName.length() > 20)
            throw new IllegalArgumentException("입력한 이름의 길이가 너무 깁니다.");
        if(!Pattern.matches("^[0-9a-zA-Z가-힣]*$", userName))
            throw new IllegalArgumentException("이름의 특수문자는 들어갈 수 없습니다.");
    }

    private void isValidEmail(Member member) {
        String email = member.getEmail();
        List<Member> list = em.createQuery("select m from Member m where m.email =: email", Member.class)
                .setParameter("email", email)
                .setFirstResult(0)
                .setMaxResults(1)
                .getResultList();
        Member result = null;
        if(!list.isEmpty())
            result = list.get(0);
        if(result != null)
            throw new IllegalArgumentException("이미 존재하는 email입니다.");

        if(email.length() > 30 || email.length() < 10)
            throw new IllegalArgumentException("Email의 길이는 10 ~ 20의 값을 가져야합니다.");

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches())
            throw new IllegalArgumentException("잘못된 이메일 형식입니다 " + email);
    }
}
