package com.community.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidation {

    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,50}$";

    public static void isValidPassword(String password) {
        if(password.length() <= 8)
            throw new IllegalArgumentException("8글자 이상의 비밀번호를 입력하세요");
        if(password.length() > 50)
            throw new IllegalArgumentException("50글자 이하의 비밀번호를 입력하세요");

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        if(!matcher.matches())
            throw new IllegalArgumentException("잘못된 비밀번호 형식입니다.");
    }
}
