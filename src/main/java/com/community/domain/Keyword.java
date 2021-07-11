package com.community.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "keywords")
@Getter @Setter
public class Keyword {

    @Id
    @GeneratedValue
    @Column(name="keyword_id")
    private Long id;

    @OneToMany(mappedBy = "keyword")
    private List<Tag> tag;

    private String word;
}
