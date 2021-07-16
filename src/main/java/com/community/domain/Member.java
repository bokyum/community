package com.community.domain;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;



@Entity
@Getter @Setter
@NoArgsConstructor
public class Member{

    @Id @GeneratedValue
    @Column(name="member_id", updatable = false)
    private Long id;

    @NotEmpty
    private String username;

    @NotEmpty
    @Column(unique = true)
    private String email;


    @NotEmpty
    private String password;

    @Embedded
    private Role role;

    private Integer loginCount;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createAt;
    @LastModifiedDate
    private LocalDateTime lastModified ;


    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Review> reviews = new ArrayList<>();


    public Member(@NotEmpty String username, @NotEmpty String email, @NotEmpty String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = Role.USER;
    }
}
