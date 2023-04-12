package com.project.jinhyo_devlog_backend.service.hashTag;

import com.project.jinhyo_devlog_backend.entity.hashTag.HashTag;
import com.project.jinhyo_devlog_backend.entity.post.Post;
import com.project.jinhyo_devlog_backend.entity.postTag.PostHashTag;
import com.project.jinhyo_devlog_backend.response.BasicResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HashTagService {

    List<HashTag> findHashTags(Post post);

    List<PostHashTag> checkHashTag(Post post, List<String> hashTags);

    void deleteHashTag(Post post);

    ResponseEntity<BasicResponse> getHashTag();

}
