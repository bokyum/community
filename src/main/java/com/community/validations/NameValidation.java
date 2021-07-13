package com.community.validations;

import com.community.domain.User;
import com.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class NameValidation {
    private final UserService userService;

    @Autowired
    public NameValidation(UserService userService) {
        this.userService = userService;
    }

    public void isValidName(String username) {
        if(username.length() > 30)
            throw new IllegalArgumentException("입력한 username의 길이가 너무 깁니다.");
        if(!Pattern.matches("^[0-9a-zA-Z가-힣]*$", username))
            throw new IllegalArgumentException("username의 특수문자는 들어갈 수 없습니다.");
        User user = userService.findUserByEmail(username);
        if(user != null)
            throw new IllegalArgumentException("이미 존재하는 username 입니다.");
    }


}
