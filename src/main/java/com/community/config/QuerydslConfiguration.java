package com.community.config;

import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
@EnableJpaAuditing
public class QuerydslConfiguration {

    @PersistenceContext
    private EntityManager em;

    @Bean
    public JPAQueryFactory jqpQueryFactory() {
        return new JPAQueryFactory(em);
    }
}
