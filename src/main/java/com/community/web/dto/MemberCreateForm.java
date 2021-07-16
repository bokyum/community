package com.community.web.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class MemberCreateForm {


    @NotNull
    @Min(8)
    @Column(unique = true)
    private String username;

    @Min(10)
    @Column(unique = true)
    private String email;


    @Min(10)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>])$" )
    private String password;

}
