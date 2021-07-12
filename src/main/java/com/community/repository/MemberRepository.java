package com.community.repository;

import com.community.domain.Member;
import com.community.dto.RequestMemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Slf4j
public class MemberRepository {


    private final EntityManager em;

    @Autowired
    public MemberRepository(EntityManager em) {

        this.em = em;
    }

    public void save(Member member) {
        try {
            isValidEmail(member.getEmail());
            em.persist(member);
        }
        catch (Exception e) {
            throw e;
        }
    }


    public Member findById(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name =: name", Member.class)
                .getResultList();

    }


    public void updateById(Long id, RequestMemberDto requestMemberDto) {
        try {

            Member member = findById(id);
            if (requestMemberDto.getEmail() != null) {
                isValidEmail(requestMemberDto.getEmail());
                member.setEmail(requestMemberDto.getEmail());
            }
            if (requestMemberDto.getName() != null)
                member.setName(requestMemberDto.getName());
            if (requestMemberDto.getPassword() != null)
                member.setPassword(requestMemberDto.getPassword());

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void deleteById(Long id) {
        Member member = findById(id);
        if(member == null)
            throw new IllegalArgumentException("회원 정보가 없음");
        em.remove(member);
        member = null;
    }



    private void isValidEmail(String email) {
        List<Member> list = em.createQuery("select m from Member m where m.email =: email", Member.class)
                .setParameter("email", email)
                .setFirstResult(0)
                .setMaxResults(1)
                .getResultList();
        Member result = null;
        if(!list.isEmpty())
            result = list.get(0);
        if(result != null)
            throw new IllegalArgumentException();
    }
}
