package com.community.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity(name="users")
@Getter @Setter
public class User {

    @Id @GeneratedValue
    @Column(name="user_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Column(length = 20)
    private String username;
    @Column(length = 30)
    private String email;
    @Column(length=50)
    private String password;

    private Integer loginCount;
    private LocalDateTime lastLoginAt;
    @Column(updatable = false)
    private LocalDateTime createAt;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();

}
