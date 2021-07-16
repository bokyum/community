package com.community.domain.repository;

import com.community.api.v1.dto.members.LoginForm;
import com.community.domain.Member;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class SelectMemberRepository {

    private final EntityManager em;

    @Autowired
    public SelectMemberRepository(EntityManager em) {
        this.em = em;
    }

    public Member findByEmailAndPassword(LoginForm loginForm) {
        List<Member> resultList = em.createQuery("select m from Member m " +
                "where m.email =: email and m.password =: password", Member.class)
                .setParameter("email", loginForm.getEmail())
                .setParameter("password", loginForm.getPassword())
                .getResultList();
        if(resultList.isEmpty())
            return null;
        else
            return resultList.get(0);
    }
}
