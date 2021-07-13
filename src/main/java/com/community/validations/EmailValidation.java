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
public class EmailValidation {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private final UserService userService;

    @Autowired
    public EmailValidation(UserService userService) {
        this.userService = userService;
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
