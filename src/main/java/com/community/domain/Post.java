package com.community.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Post {

    @Id
    @GeneratedValue
    @Column(name="post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private LocalDateTime createdAt;

    private String title;

    @OneToMany(mappedBy = "post")
    private List<Tag> tag;

    @OneToMany(mappedBy = "post")
    private List<PostReview> reviews = new ArrayList<>();
}
