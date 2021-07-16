package com.community.domain.repository;

import com.community.api.v1.dto.members.LoginForm;
import com.community.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail();

}
