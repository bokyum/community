package com.community.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Review {

    @Id @GeneratedValue
    @Column(name="review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member Member;


    private String comment;

    private LocalDateTime lastComment;

    private Integer numOfLike;
    private Integer numOfDislike;
}
