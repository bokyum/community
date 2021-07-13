package com.community.dto;

import com.community.domain.Umjjal;
import lombok.Data;

import java.util.List;

@Data
public class ResponseMemberDto {

    private String name;
    private String email;
    private List<Umjjal> umjjals;

    public ResponseMemberDto() {
    }

    public ResponseMemberDto(String name, String email, List<Umjjal> umjjals) {
        this.name = name;
        this.email = email;
        this.umjjals = umjjals;
    }
}
