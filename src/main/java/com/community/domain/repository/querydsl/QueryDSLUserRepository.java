package com.community.domain.repository.querydsl;


import com.community.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.community.domain.QUser;


@Repository
public class QueryDSLUserRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;


    public QueryDSLUserRepository(JPAQueryFactory queryFactory) {
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
