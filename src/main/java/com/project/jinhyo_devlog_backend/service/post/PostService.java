package com.project.jinhyo_devlog_backend.service.post;

import com.project.jinhyo_devlog_backend.dto.PostRequest;
import com.project.jinhyo_devlog_backend.response.BasicResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface PostService {

    ResponseEntity<BasicResponse> getPost(Long id, String hashTag);

    ResponseEntity<BasicResponse> addPost(PostRequest postRequest) throws IOException;

    ResponseEntity<BasicResponse> addImage(MultipartFile multipartFile) throws IOException;

    ResponseEntity<BasicResponse> editPost(PostRequest postRequest, List<MultipartFile> multipartFiles) throws IOException;

    ResponseEntity<BasicResponse> deletePost(Long id);

}
