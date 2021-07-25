package com.community.api.v1.dto.request.post;

import com.community.domain.Review;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class PostResponse {

    private String username;
    private String title;
    private String content;
    private List<String> tags = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedDate;

    @Builder
    public PostResponse(String username, String title, String content, LocalDateTime createdAt, LocalDateTime lastModifiedDate) {
        this.username = username;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.lastModifiedDate = lastModifiedDate;
    }
}
