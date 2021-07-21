package com.community.domain;



import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User{

    @Id @GeneratedValue
    @Column(name="user_id", updatable = false)
    private Long id;

    @NotEmpty
    @Column(unique = true)
    private String username;

    @NotEmpty
    private String password;





    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();


    public User(@NotEmpty String username, @NotEmpty String password) {
        this.username = username;
        this.password = password;
    }


}
