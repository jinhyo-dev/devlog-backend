package com.project.jinhyo_devlog_backend.service.image;

import com.project.jinhyo_devlog_backend.entity.image.Image;
import com.project.jinhyo_devlog_backend.entity.image.ImageRepository;
import com.project.jinhyo_devlog_backend.entity.post.Post;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    private final String absolutePath = new File("./image").getAbsolutePath() + File.separator;

    private final String path = "image" + File.separator;

    @Override
    public List<Image> saveImage(List<MultipartFile> multipartFiles) throws IOException {
        List<Image> images = new ArrayList<>();

        if (multipartFiles != null) {
            File file = new File(path);

            if (!file.exists()) {
                if (!file.mkdir()) {
                    log.info("file was not successful");
                }
            }

            for (MultipartFile multipartFile : multipartFiles) {
                log.info("fileName: {}", multipartFile.getOriginalFilename());

                String originalFileExtension;
                String contentType = multipartFile.getContentType();

                if (ObjectUtils.isEmpty(contentType)) {
                    break;
                } else {
                    if (contentType.contains("image/jpeg") || contentType.contains("image/heic") || contentType.contains("image/jpg")) {
                        originalFileExtension = ".jpeg";
                    } else if (contentType.contains("image/png")) {
                        originalFileExtension = ".png";
                    } else if (contentType.contains("image/gif")) {
                        originalFileExtension = ".gif";
                    } else {
                        break;
                    }
                }

                String fileSaveName = UUID.randomUUID() + originalFileExtension;

                Image image = Image.builder().
                        id(null)
                        .fileName(multipartFile.getOriginalFilename())
                        .fileSaveName(fileSaveName)
                        .filePath(path + fileSaveName + File.separator)
                        .fileUrl("아직")
                        .build();

                images.add(image);

                imageRepository.save(image);

                file = new File(absolutePath + File.separator + fileSaveName);
                multipartFile.transferTo(file);
            }
        } else {
            log.info("MultipartFile Not Found");
        }

        return images;
    }

    @Override
    public void deleteImage(Post post) {
        List<Image> images = List.of(post.getImage());
        post.setImage(null);

        for (Image image : images) {
            File file = new File(path + image.getFileSaveName());

            if (file.exists()) {
                if (file.delete()) {
                    log.info("{} is delete", image.getFileSaveName());
                } else {
                    log.info("{} is not delete", image.getFileSaveName());
                }
            } else {
                log.info("{} is not found", image.getFileSaveName());
            }
        }
        imageRepository.deleteAll(images);
    }

}
