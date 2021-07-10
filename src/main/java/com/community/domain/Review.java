package com.community.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name="reviews")
@Getter @Setter
public class Review {

    @Id @GeneratedValue
    @Column(name="review_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    private String comment;

    private String lastCommentAt;

    private Integer like;
    private Integer disLike;
}
