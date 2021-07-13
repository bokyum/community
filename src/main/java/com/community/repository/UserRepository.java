package com.community.repository;

import com.community.domain.User;
import com.community.dto.RequestUserDto;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    
    User findById(Long id);
    
    Optional<User> findByUsername(String username);

    void deleteById(Long id);

    User findByEmail(String email);

    void delete(User user);
}
