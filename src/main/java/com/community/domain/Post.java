package com.community.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.swing.text.html.parser.TagElement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name="posts")
@Getter @Setter
public class Post {

    @Id
    @GeneratedValue
    @Column(name="post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private LocalDateTime createdAt;

    private String title;

    @OneToOne(mappedBy = "post")
    private Tag tag;

    @OneToMany(mappedBy = "post")
    private List<Review> reviews = new ArrayList<>();
}
