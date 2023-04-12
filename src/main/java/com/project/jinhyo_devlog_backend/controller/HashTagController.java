package com.project.jinhyo_devlog_backend.controller;

import com.project.jinhyo_devlog_backend.response.BasicResponse;
import com.project.jinhyo_devlog_backend.service.hashTag.HashTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hashtag")
public class HashTagController {

    private final HashTagService hashTagService;

    @GetMapping
    public ResponseEntity<BasicResponse> getHashTag() {
        return hashTagService.getHashTag();
    }


}
