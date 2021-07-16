package com.community.domain;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;



@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member{

    @Id @GeneratedValue
    @Column(name="member_id", updatable = false)
    private Long id;



    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String email;
    private String password;

    @Embedded
    private Role role;

    private Integer loginCount;

    @UpdateTimestamp
    private LocalDateTime lastLoginAt;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createAt;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Review> Reviews = new ArrayList<>();


    public Member(String name, String password) {
        this.name = name;
        this.password = password;
        this.loginCount = 0;
    }
}
