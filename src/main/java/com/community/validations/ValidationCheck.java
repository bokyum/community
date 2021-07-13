package com.community.validations;

import com.community.domain.User;
import com.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Transactional(readOnly = true)
public class ValidationCheck {
    private final UserService userService;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,50}$";


    @Autowired
    public ValidationCheck(UserService userService) {
        this.userService = userService;
    }

    public void isValidPassword(String password) {
;
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        if(!matcher.matches())
            throw new IllegalArgumentException("잘못된 비밀번호 형식입니다.");
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

    public void isValidNameWhenUpdate(String username) {
        if(username.length() > 30)
            throw new IllegalArgumentException("입력한 username의 길이가 너무 깁니다.");
        if(!Pattern.matches("^[0-9a-zA-Z가-힣]*$", username))
            throw new IllegalArgumentException("username의 특수문자는 들어갈 수 없습니다.");
    }

    public void isValidEmail(String email) {
        if(email.length() > 30 || email.length() < 10)
            throw new IllegalArgumentException("Email의 길이는 10 ~ 30의 값을 가져야합니다.");
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches())
            throw new IllegalArgumentException("잘못된 이메일 형식입니다 ");
        User user = userService.findUserByEmail(email);
        if(user != null)
            throw new IllegalArgumentException("이미 존재하는 email 입니다.");
    }

    public void isValidEmailWhenUpdate(String email) {
        if(email.length() > 30 || email.length() < 10)
            throw new IllegalArgumentException("Email의 길이는 10 ~ 30의 값을 가져야합니다.");
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches())
            throw new IllegalArgumentException("잘못된 이메일 형식입니다 ");
    }
}
