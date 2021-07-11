package com.community.domain;


import com.community.domain.Authority;
import com.community.domain.Post;
import com.community.domain.PostReview;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name="member_id", updatable = false)
    private Long id;

    @Column(length = 20)
    private String name;
    @Column(length = 20)
    private String email;
    @Column(length=20)
    private String password;

    @Enumerated(EnumType.STRING)
    private Authority authority;


    private Integer loginCount;
    private LocalDateTime lastLoginAt;
    @Column(updatable = false)
    private LocalDateTime createAt;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<PostReview> postReviews = new ArrayList<>();

    public Member() {
    }

    public Member(String name, String email, String password, Authority authority) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.authority = authority;
        this.loginCount = 0;
        this.lastLoginAt = LocalDateTime.now();
        this.createAt = LocalDateTime.now();
    }

    public Member(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.authority = Authority.GUEST;
        this.loginCount = 0;
        this.lastLoginAt = LocalDateTime.now();
        this.createAt = LocalDateTime.now();
    }
}
