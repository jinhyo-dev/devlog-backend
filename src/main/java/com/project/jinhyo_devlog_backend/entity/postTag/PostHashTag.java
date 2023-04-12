package com.project.jinhyo_devlog_backend.entity.postTag;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.jinhyo_devlog_backend.entity.hashTag.HashTag;
import com.project.jinhyo_devlog_backend.entity.post.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostHashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "hash_tag_id")
    private HashTag hashTag;

}
