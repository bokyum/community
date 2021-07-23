package com.community.domain.repository.querydsl;


import com.community.api.v1.dto.request.users.UserInfoRequest;
import com.community.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.community.domain.QUser;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Repository
public class UserRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;


    public UserRepositorySupport(JPAQueryFactory queryFactory) {
        super(User.class);
        this.queryFactory = queryFactory;
    }

    public List<User> findByUsername(String username) {
        QUser user = QUser.user;
        return queryFactory
                .selectFrom(user)
                .where(user.username.eq(username))
                .fetch();
    }


}
