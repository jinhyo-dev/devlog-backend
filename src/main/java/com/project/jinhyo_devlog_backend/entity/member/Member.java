package com.project.jinhyo_devlog_backend.entity.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.jinhyo_devlog_backend.entity.post.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String username;

    @JsonIgnore
    private String password;

    @OneToMany
    @JsonIgnore
    private List<Post> posts;

    private String role;

}
