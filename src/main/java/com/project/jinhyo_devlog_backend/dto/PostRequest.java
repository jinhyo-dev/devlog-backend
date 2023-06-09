package com.project.jinhyo_devlog_backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostRequest {

    private Long postId;

    private Long memberId;

    private String title;

    private String info;

    private String content;

    private String image;

    private List<String> hashTag;

}
