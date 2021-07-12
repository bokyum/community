package com.community.dto;

import com.community.domain.Post;
import lombok.Data;

import java.util.List;

@Data
public class ResponseMemberDto {

    private String name;
    private String email;
    private List<Post> posts;

    public ResponseMemberDto(String name, String email,List<Post> posts) {
        this.name = name;
        this.email = email;
        this.posts = posts;
    }
}
