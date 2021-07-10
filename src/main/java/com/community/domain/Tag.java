package com.community.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tags")
@Getter @Setter
public class Tag {

    @Id @GeneratedValue
    @Column(name="tag_id")
    private Long id;

    @OneToMany
    @JoinColumn(name="keyword_id")
    private List<Keyword> keywords = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post post;
}
