package com.project.jinhyo_devlog_backend.dto;

import com.project.jinhyo_devlog_backend.entity.hashTag.HashTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HashTagDto {

    private String hashtag;

    private int count;

    public HashTagDto toHashTagResponse(HashTag hashTag) {
        return HashTagDto.builder()
                .hashtag(hashTag.getName())
                .count(hashTag.getTotal())
                .build();
    }

}
