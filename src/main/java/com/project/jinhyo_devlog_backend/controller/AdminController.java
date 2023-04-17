package com.project.jinhyo_devlog_backend.controller;

import com.project.jinhyo_devlog_backend.dto.PostRequest;
import com.project.jinhyo_devlog_backend.response.BasicResponse;
import com.project.jinhyo_devlog_backend.service.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final PostService postService;

    @PostMapping("/post")
    public ResponseEntity<BasicResponse> addPost(@RequestBody PostRequest postRequest) throws IOException {
        return postService.addPost(postRequest);
    }

    @PostMapping("/image")
    public ResponseEntity<BasicResponse> addPost(@RequestPart(value = "image", required = false) MultipartFile multipartFile) throws IOException {
        return postService.addImage(multipartFile);
    }

    @PostMapping("/edit")
    public ResponseEntity<BasicResponse> editPost(@RequestPart PostRequest postRequest,
                                                  @RequestPart(value = "image", required = false) List<MultipartFile> multipartFiles) throws IOException {
        return postService.editPost(postRequest, multipartFiles);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BasicResponse> deletePost(@RequestParam Long id) {
        return postService.deletePost(id);
    }

}
