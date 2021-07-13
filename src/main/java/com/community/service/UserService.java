package com.community.service;

import com.community.domain.User;
import com.community.dto.LoginUserDto;
import com.community.dto.RequestUserDto;
import com.community.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //회원가입 후 아이디 반환
    @Transactional(readOnly = false)
    public Long join(RequestUserDto requestUserDto){
        User user = new User(requestUserDto.getUsername(),
                requestUserDto.getEmail(),
                requestUserDto.getPassword()
                );
        userRepository.save(user);
        return user.getId();

    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Long id) {
        User user = userRepository.findById(id);
        return user;
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional(readOnly = false)
    public User updateUserById(Long id, RequestUserDto requestUserDto) {
        User user = userRepository.findById(id);
        user.setUsername(requestUserDto.getUsername());
        user.setEmail(requestUserDto.getEmail());
        user.setPassword(requestUserDto.getPassword());
        return user;
    }

    @Transactional(readOnly = false)
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = false)
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

}
