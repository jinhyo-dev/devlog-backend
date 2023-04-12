package com.project.jinhyo_devlog_backend.controller;

import com.project.jinhyo_devlog_backend.response.BasicResponse;
import com.project.jinhyo_devlog_backend.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<BasicResponse> getPost(@RequestParam(required = false) Long id, @RequestParam(required = false) String hashTag) {
        return postService.getPost(id, hashTag);
    }



}
