package com.project.jinhyo_devlog_backend.service.hashTag;

import com.project.jinhyo_devlog_backend.dto.HashTagResponse;
import com.project.jinhyo_devlog_backend.entity.hashTag.HashTag;
import com.project.jinhyo_devlog_backend.entity.hashTag.HashTagRepository;
import com.project.jinhyo_devlog_backend.entity.post.Post;
import com.project.jinhyo_devlog_backend.entity.postTag.PostHashTag;
import com.project.jinhyo_devlog_backend.entity.postTag.PostHashTagRepository;
import com.project.jinhyo_devlog_backend.response.BasicResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class HashTagServiceImpl implements HashTagService {

    private final HashTagRepository hashTagRepository;

    private final PostHashTagRepository postHashTagRepository;

    @Override
    public List<HashTag> findHashTags(Post post) {
        List<HashTag> hashTags = new ArrayList<>();

        for (PostHashTag postHashTag : post.getPostHashTags()) {
            hashTags.add(postHashTag.getHashTag());
        }

        return hashTags;
    }

    @Override
    public List<PostHashTag> checkHashTag(Post post, List<String> hashTags) {
        List<PostHashTag> postHashTags = new ArrayList<>();

        deleteHashTag(post);

        for (String hashTagName : hashTags) {
            Optional<HashTag> hashTagOpt = hashTagRepository.findByName(hashTagName);
            HashTag hashTag;

            if (hashTagOpt.isPresent()) {
                hashTag = hashTagOpt.get();
                hashTag.setTotal(hashTag.getTotal() + 1);
            } else {
                log.info("not found hash tag name: {}", hashTagName);

                hashTag = HashTag.builder()
                        .id(null)
                        .name(hashTagName)
                        .total(1)
                        .build();
            }

            hashTagRepository.save(hashTag);

            PostHashTag postHashTag = PostHashTag.builder()
                    .id(null)
                    .post(post)
                    .hashTag(hashTag)
                    .build();

            postHashTags.add(postHashTag);
            postHashTagRepository.save(postHashTag);
        }

        return postHashTags;
    }

    @Override
    public void deleteHashTag(Post post) {
        if (post.getPostHashTags() != null) {
            for (PostHashTag postHashTag : post.getPostHashTags()) {
                HashTag hashTag = postHashTag.getHashTag();
                hashTag.setTotal(hashTag.getTotal() - 1);

                hashTagRepository.save(hashTag);
            }
        }
    }

    @Override
    public ResponseEntity<BasicResponse> getHashTag() {
        List<HashTag> hashTags = hashTagRepository.findAll();
        List<HashTagResponse> hashTagResponses = new ArrayList<>();
        BasicResponse basicResponse;

        for (HashTag hashTag : hashTags) {
            if (hashTag.getTotal() != 0) {
                hashTagResponses.add(new HashTagResponse().toHashTagResponse(hashTag));
            }
        }

        if (!hashTagResponses.isEmpty()) {
            basicResponse = BasicResponse.builder()
                    .code(HttpStatus.OK.value())
                    .httpStatus(HttpStatus.OK)
                    .message("헤시태그를 정상적으로 찾았습니다.")
                    .count(hashTagResponses.size())
                    .result(hashTagResponses)
                    .build();
        } else {
            basicResponse = BasicResponse.builder()
                    .code(HttpStatus.OK.value())
                    .httpStatus(HttpStatus.OK)
                    .message("헤시테그를 찾을 수 없습니다.")
                    .count(0)
                    .result(null)
                    .build();
        }

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

}
