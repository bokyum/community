package com.community.api.v1.service;

import com.community.api.v1.dto.AuthForm;
import com.community.domain.User;
import com.community.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User login(AuthForm form) {
        User user = userRepository.findByUsernameAndPassword(form.getUsername(), form.getPassword());
        return user;
    }

    public User join(AuthForm form) {
        return userRepository.save(new User(form.getUsername(), form.getPassword()));
    }

    public User findUserById(Long id) {
        return userRepository.findUserById(id);
    }

}
