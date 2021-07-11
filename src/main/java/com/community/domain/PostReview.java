package com.community.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class PostReview {

    @Id @GeneratedValue
    @Column(name="post_review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;


    private String comment;

    private String lastCommentAt;

    private Integer numOfLike;
    private Integer numOfDislike;
}
