package com.community.api.v1.dto.request.post;

import com.community.domain.Tag;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
public class PostRequest {
    private String username;
    private String title;
    private String content;
    private List<String> tags = new ArrayList<>();


}
