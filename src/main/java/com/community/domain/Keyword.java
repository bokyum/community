package com.community.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Keyword {

    @Id
    @GeneratedValue
    @Column(name="keyword_id")
    private Long id;

    @OneToMany(mappedBy = "keyword")
    private List<Tag> tag=new ArrayList<>();

    private String word;

    public Keyword(String word) {
        this.word = word;
    }

    public void addTag(Tag tag) {
        this.tag.add(tag);
    }
}
