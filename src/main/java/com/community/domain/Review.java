package com.community.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Review {

    @Id @GeneratedValue
    @Column(name="review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @NotEmpty
    private String comment;

    private Integer numOfLike;
    private Integer numOfDislike;
}
