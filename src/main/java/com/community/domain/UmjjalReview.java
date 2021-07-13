package com.community.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class UmjjalReview {

    @Id @GeneratedValue
    @Column(name="umjjal_review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Umjjal umjjal;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


    private String comment;

    private String lastCommentAt;

    private Integer numOfLike;
    private Integer numOfDislike;
}
