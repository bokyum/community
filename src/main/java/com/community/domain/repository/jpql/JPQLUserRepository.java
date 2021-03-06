package com.community.domain.repository.jpql;

import com.community.api.v1.dto.request.users.UserInfoRequest;
import com.community.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

@Repository
public class JPQLUserRepository {


    private final EntityManager em;

    @Autowired
    public JPQLUserRepository(EntityManager em) {
        this.em = em;
    }

}
