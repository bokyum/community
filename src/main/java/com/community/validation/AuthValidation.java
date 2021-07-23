package com.community.validation;

import com.community.api.v1.service.UserService;
import com.community.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Transactional(readOnly = false)
public class AuthValidation {

    private final UserService userService;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,50}$";


    @Autowired
    public AuthValidation(UserService userService) {
        this.userService = userService;
    }

    public void isValidPasswordJoin(String password) {
        if(password.length() < 8 || password.length() > 50)
            throw new IllegalArgumentException("비밀번호는 8 ~ 50 사이의 문자여야 합니다.");
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        if(!matcher.matches())
            throw new IllegalArgumentException("비밀번호는 숫자, 소문자, 대문자, 특수문자를 하나 이상의 조합이여야합니다.");
    }

    public void isValidNameJoin(String username) {
        if(username.length() > 30 || username.length() < 8)
            throw new IllegalArgumentException("아이디는 8 ~ 30 사이의 길이여야 합니다..");
        if(!Pattern.matches("^[0-9a-zA-Z가-힣]*$", username))
            throw new IllegalArgumentException("아이디에 특수문자는 들어갈 수 없습니다.");
        User user = userService.findUserByUsername(username);
        if(user != null)
            throw new IllegalArgumentException("이미 존재하는 아이디 입니다.");
    }


    public void isValidEmailJoin(String email) {
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

    public void isValidPassword(String password) {
        if(password.length() < 8 || password.length() > 50)
            throw new IllegalArgumentException("비밀번호는 8 ~ 50 사이의 문자여야 합니다.");
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        if(!matcher.matches())
            throw new IllegalArgumentException("비밀번호는 숫자, 소문자, 대문자, 특수문자를 하나 이상의 조합이여야합니다.");
    }

    public void isValidName(String username) {
        if(username.length() > 30 || username.length() < 8)
            throw new IllegalArgumentException("아이디는 8 ~ 30 사이의 길이여야 합니다..");
        if(!Pattern.matches("^[0-9a-zA-Z가-힣]*$", username))
            throw new IllegalArgumentException("아이디에 특수문자는 들어갈 수 없습니다.");
    }

    public void isValidNameWhenUpdate(Long id, String username) {
        if(username.length() > 30 || username.length() < 8)
            throw new IllegalArgumentException("아이디는 8 ~ 30 사이의 길이여야 합니다..");
        if(!Pattern.matches("^[0-9a-zA-Z가-힣]*$", username))
            throw new IllegalArgumentException("아이디 특수문자는 들어갈 수 없습니다.");
        User user = userService.findUserById(id);
        if(user.getUsername().equals(username))
            return;
        User userByUsername = userService.findUserByUsername(username);
        if(userByUsername != null)
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
    }

    public void isValidEmailWhenUpdate(Long id, String email) {
        if(email.length() > 30 || email.length() < 10)
            throw new IllegalArgumentException("Email의 길이는 10 ~ 30의 값을 가져야합니다.");
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches())
            throw new IllegalArgumentException("잘못된 이메일 형식입니다 ");
        User user = userService.findUserById(id);
        if(user.getEmail().equals(email))
            return;
        User userByEmail = userService.findUserByEmail(email);
        if(userByEmail != null)
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
    }

}
