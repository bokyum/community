package com.community.domain;

import com.community.domain.Post;
import lombok.Getter;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String name;
    private LocalDateTime joinDate;
    private LocalDateTime modifiedDate;

    private String password;



}
