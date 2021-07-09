package com.community.domain;

import lombok.Getter;

import javax.persistence.*;

@Table(name="post")
@Entity
@Getter
public class Post {

    @Id @GeneratedValue
    @Column(name="post_id")
    private Long id;

    private String title;
    private String contents;


}
