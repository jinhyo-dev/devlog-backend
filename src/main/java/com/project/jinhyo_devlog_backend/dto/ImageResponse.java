package com.project.jinhyo_devlog_backend.dto;

import com.project.jinhyo_devlog_backend.entity.image.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponse {

    private Image image;

}
