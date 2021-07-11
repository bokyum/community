package com.community.repository;

import com.community.domain.Member;
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
            isValidEmail(member);
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
            throw new IllegalArgumentException();
    }
}
