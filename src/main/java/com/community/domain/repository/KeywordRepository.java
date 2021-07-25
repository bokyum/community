package com.community.domain.repository;

import com.community.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    boolean existsByWord(String word);
    Optional<Keyword> findByWord(String word);
}
