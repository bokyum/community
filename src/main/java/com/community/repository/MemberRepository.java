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
        em.persist(member);
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


    public Member updateById(Long id, RequestMemberDto requestMemberDto) {
        Member member = findById(id);
        member.setEmail(requestMemberDto.getEmail());
        member.setName(requestMemberDto.getName());
        member.setPassword(requestMemberDto.getPassword());
        return member;
    }

    public void deleteById(Long id) {
        Member member = findById(id);
        em.remove(member);
        member = null;
    }

    public Member findByEmail(String email) {
        List<Member> members = em.createQuery("select m from Member m where m.email =: email", Member.class)
                .setParameter("email", email)
                .getResultList();
        if(members.isEmpty())
            return null;
        return members.get(0);
    }

    public void deleteMember(Member member) {
        em.remove(member);
        member = null;
    }
}
