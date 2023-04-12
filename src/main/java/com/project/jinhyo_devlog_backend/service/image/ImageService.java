package com.project.jinhyo_devlog_backend.service.image;

import com.project.jinhyo_devlog_backend.entity.image.Image;
import com.project.jinhyo_devlog_backend.entity.post.Post;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface ImageService {

    List<Image> saveImage(List<MultipartFile> multipartFiles) throws IOException;

    void deleteImage(Post post);

}
