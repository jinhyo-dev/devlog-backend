package com.project.jinhyo_devlog_backend.entity.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.jinhyo_devlog_backend.entity.image.Image;
import com.project.jinhyo_devlog_backend.entity.member.Member;
import com.project.jinhyo_devlog_backend.entity.postTag.PostHashTag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @OneToMany
    private List<Image> images;

    @JsonIgnore
    @OneToMany(mappedBy = "post")
    private List<PostHashTag> postHashTags;

    private Integer view;

    private String createdAt;

    @ManyToOne
    private Member member;

}
