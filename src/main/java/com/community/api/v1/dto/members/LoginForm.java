package com.community.api.v1.dto.members;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;

@Data
public class LoginForm {

    @Email
    @Size(min=8)
    private String email;

    @NotBlank
    @Size(min=8)
    private String password;
}
