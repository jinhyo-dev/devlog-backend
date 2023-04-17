package com.project.jinhyo_devlog_backend.service.member;

import com.project.jinhyo_devlog_backend.dto.SignRequest;
import com.project.jinhyo_devlog_backend.dto.SignResponse;
import com.project.jinhyo_devlog_backend.entity.enums.Role;
import com.project.jinhyo_devlog_backend.entity.member.Member;
import com.project.jinhyo_devlog_backend.entity.member.MemberRepository;
import com.project.jinhyo_devlog_backend.security.JWTProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final JWTProvider jwtProvider;

    @Override
    public SignResponse login(SignRequest request, HttpServletResponse response) {
        Member member = memberRepository.findByUsername(request.getUsername()).orElseThrow(() ->
                new BadCredentialsException("잘못된 계정정보입니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new BadCredentialsException("잘못된 계정정보입니다.");
        }

        String token = jwtProvider.createToken(member.getUsername(), member.getRole());

        ResponseCookie cookie = ResponseCookie.from("accessToken", token)
                .maxAge(7 * 24 * 60 * 60)
                .path("/")
                .secure(true)
                .sameSite("None")
                .httpOnly(true)
                .build();

        response.setHeader("Set-Cookie", cookie.toString());

        return SignResponse.builder()
                .name(member.getName())
                .username(member.getUsername())
                .roles(List.of(member.getRole()))
                .token(token)
                .build();

    }

}