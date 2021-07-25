package com.community.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue
    @Column(name="post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @NotEmpty
    private String title;

    @NotEmpty
    @Lob
    private String content;

    @OneToMany(mappedBy = "keyword")

    private List<Tag> tag = new ArrayList<>();


    @OneToMany(mappedBy = "post")
    private List<Review> reviews = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;


    @Builder
    public Post(User user, @NotEmpty String title, @NotEmpty String content) {
        this.title = title;
        this.content = content;

        this.createdAt = LocalDateTime.now();
        this.lastModifiedDate = LocalDateTime.now();

        this.user = user;
        user.addPost(this);
    }

    public void setTag(List<Tag> tag) {
        this.tag = tag;
    }

    public void editPost(User user, String title, String content, List<String> tag) {
    }
}
