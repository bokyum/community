package com.community.api.v1.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class JoinRequest {

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    private Set<String> role;

    @NotBlank
    private String password;
}
