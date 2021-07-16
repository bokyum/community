package com.community.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue
    @Column(name="post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime lastModified;

    @OneToMany(mappedBy = "post")
    private List<Tag> tag;

    @OneToMany(mappedBy = "post")
    private List<Review> reviews = new ArrayList<>();
}
