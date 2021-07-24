package com.community.api.v1.service;

import com.community.api.v1.dto.request.users.UserInfoRequest;
import com.community.domain.User;
import com.community.domain.repository.UserRepository;
import com.community.domain.repository.jpql.JPQLUserRepository;
import com.community.domain.repository.querydsl.UserRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JPQLUserRepository jpqlUserRepository;


    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User userDetails(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
            new IllegalArgumentException("존재하지 않는 회원 정보입니다.")
        );
    }

    @Transactional(readOnly = false)
    public void join(User user) {
        userRepository.save(user);
    }

    @Transactional(readOnly = false)
    public User updateUserInfo(Long id, UserInfoRequest req) {
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty())
            throw new IllegalArgumentException("존재하지 않는 회원 정보입니다.");

       return jpqlUserRepository.updateUserBasicInfo(user.get(), req);
    }

    @Transactional(readOnly = false)
    public void deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new IllegalArgumentException("존재하지 않는 회원 정보입니다");
        userRepository.delete(user.get());
    }


}
