package com.community.api.v1.dto.members;

import com.community.domain.Post;
import com.community.domain.Review;
import com.community.domain.Role;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class MemberDetailForm {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Integer loginCount;
    private LocalDateTime createAt;
    private LocalDateTime lastModified ;



    private List<Post> posts ;


    private List<Review> reviews;
}
