package com.project.jinhyo_devlog_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HashTagResponse {

    private Long postCount;

    private List<HashTagDto> hashTags;

}
