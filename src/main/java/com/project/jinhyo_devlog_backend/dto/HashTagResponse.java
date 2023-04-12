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
public class HashTagResponse {

    private String hashtag;

    private int count;

    public HashTagResponse toHashTagResponse(HashTag hashTag) {
        return HashTagResponse.builder()
                .hashtag(hashTag.getName())
                .count(hashTag.getTotal())
                .build();
    }

}
