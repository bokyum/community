package com.community.validations;

import java.util.regex.Pattern;

public class NameValidation {

    public static void isValidName(String name) {
        if(name.length() > 20)
            throw new IllegalArgumentException("입력한 이름의 길이가 너무 깁니다.");
        if(!Pattern.matches("^[0-9a-zA-Z가-힣]*$", name))
            throw new IllegalArgumentException("이름의 특수문자는 들어갈 수 없습니다.");
    }
}
