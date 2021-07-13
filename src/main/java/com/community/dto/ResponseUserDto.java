package com.community.dto;

import com.community.domain.Umjjal;
import lombok.Data;

import java.util.List;

@Data
public class ResponseUserDto {

    private String username;
    private String email;
    private List<Umjjal> umjjals;

    public ResponseUserDto() {
    }

    public ResponseUserDto(String username, String email, List<Umjjal> umjjals) {
        this.username = username;
        this.email = email;
        this.umjjals = umjjals;
    }
}
