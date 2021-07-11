package com.community.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class RequestMemberDto {

    private String name;
    private String email;
    private String password;

    public RequestMemberDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
