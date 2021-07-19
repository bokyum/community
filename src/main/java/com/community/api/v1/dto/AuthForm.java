package com.community.api.v1.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AuthForm {

    @NotNull
    @Email
    private String username;

    @NotNull
    private String password;
}
