package com.community.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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
    private User user;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    @OneToMany(mappedBy = "post")
    private List<Tag> tag;

    @OneToMany(mappedBy = "post")
    private List<Review> reviews = new ArrayList<>();
}
