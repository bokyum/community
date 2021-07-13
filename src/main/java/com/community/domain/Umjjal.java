package com.community.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Umjjal {

    @Id
    @GeneratedValue
    @Column(name="umjjal_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private LocalDateTime createdAt;

    private String title;

    @OneToMany(mappedBy = "umjjal")
    private List<Tag> tag;

    @OneToMany(mappedBy = "umjjal")
    private List<UmjjalReview> reviews = new ArrayList<>();
}
