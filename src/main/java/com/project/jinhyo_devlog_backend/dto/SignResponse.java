package com.project.jinhyo_devlog_backend.dto;

import com.project.jinhyo_devlog_backend.entity.enums.Role;
import com.project.jinhyo_devlog_backend.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignResponse {

    private String name;

    private String username;

    private List<String> roles;

    private String token; 

    public SignResponse(Member member) {
        this.name = member.getName();
        this.username = member.getUsername();
        this.roles = List.of(member.getRole());
    }

}
