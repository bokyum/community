package com.community.api.v1.dto.request.users;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
public class UserInfoRequest {

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;

}
