package com.project.jinhyo_devlog_backend.controller;

import com.project.jinhyo_devlog_backend.dto.SignRequest;
import com.project.jinhyo_devlog_backend.dto.SignResponse;
import com.project.jinhyo_devlog_backend.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SignController {

    private final MemberService memberService;

    @PostMapping(value = "/login")
    public ResponseEntity<SignResponse> signin(@RequestBody SignRequest request) {
        return new ResponseEntity<>(memberService.login(request), HttpStatus.OK);
    }

}