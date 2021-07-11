package com.community.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class JoinMemberDto {

    private String userName;
    private String email;
    private String password;

    public JoinMemberDto(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
}
