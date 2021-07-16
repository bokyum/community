package com.community.api.v1.dto.members;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CreateForm {

    @NotBlank
    @Size(min=8)
    private String username;
    @NotBlank
    @Email
    @Size(min=8)
    private String email;
    @Size(min=8)
    private String password;
}
