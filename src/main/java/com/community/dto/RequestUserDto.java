package com.community.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class RequestUserDto {

    private String username;
    private String email;
    private String password;

    public RequestUserDto() {
    }

    public RequestUserDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
