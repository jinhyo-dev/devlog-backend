package com.project.jinhyo_devlog_backend.dto;

import com.project.jinhyo_devlog_backend.entity.hashTag.HashTag;
import com.project.jinhyo_devlog_backend.entity.image.Image;
import com.project.jinhyo_devlog_backend.entity.member.Member;
import com.project.jinhyo_devlog_backend.entity.post.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {

    private Long id;

    private String title;

    private String content;

    private List<Image> images;

    private List<HashTag> hashTags;

    private Integer view;

    private String createdAt;

    private Member member;

    public PostResponse toDto(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .images(post.getImages())
                .view(post.getView())
                .createdAt(post.getCreatedAt())
                .member(post.getMember())
                .build();
    }

}
