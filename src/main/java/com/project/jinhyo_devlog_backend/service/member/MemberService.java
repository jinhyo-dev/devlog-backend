package com.project.jinhyo_devlog_backend.service.member;

import com.project.jinhyo_devlog_backend.dto.SignRequest;
import com.project.jinhyo_devlog_backend.dto.SignResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {

    SignResponse login(SignRequest request, HttpServletResponse response);

}
