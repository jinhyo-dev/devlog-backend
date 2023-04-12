package com.project.jinhyo_devlog_backend.service.post;

import com.project.jinhyo_devlog_backend.dto.PostRequest;
import com.project.jinhyo_devlog_backend.dto.PostResponse;
import com.project.jinhyo_devlog_backend.entity.hashTag.HashTag;
import com.project.jinhyo_devlog_backend.entity.hashTag.HashTagRepository;
import com.project.jinhyo_devlog_backend.entity.image.Image;
import com.project.jinhyo_devlog_backend.entity.member.Member;
import com.project.jinhyo_devlog_backend.entity.member.MemberRepository;
import com.project.jinhyo_devlog_backend.entity.post.Post;
import com.project.jinhyo_devlog_backend.entity.post.PostRepository;
import com.project.jinhyo_devlog_backend.entity.postTag.PostHashTag;
import com.project.jinhyo_devlog_backend.entity.postTag.PostHashTagRepository;
import com.project.jinhyo_devlog_backend.response.BasicResponse;
import com.project.jinhyo_devlog_backend.service.hashTag.HashTagService;
import com.project.jinhyo_devlog_backend.service.image.ImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final MemberRepository memberRepository;

    private final PostRepository postRepository;

    private final HashTagRepository hashTagRepository;

    private final PostHashTagRepository postHashTagRepository;

    private final HashTagService hashTagService;

    private final ImageService imageService;

    @Override
    public ResponseEntity<BasicResponse> getPost(Long id, String hashTag) {
        BasicResponse basicResponse;

        if (id != null) {
            Optional<Post> postOpt = postRepository.findById(id);

            if (postOpt.isPresent()) {
                Post post = postOpt.get();
                post.setView(post.getView() + 1);

                postRepository.save(post);

                PostResponse postResponse = new PostResponse()
                        .toDto(post);

                postResponse.setHashTags(hashTagService.findHashTags(post));

                basicResponse = BasicResponse.builder()
                        .code(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.OK)
                        .message("게시글을 정상적으로 찾았습니다.")
                        .count(1)
                        .result(postResponse)
                        .build();
            } else {
                basicResponse = BasicResponse.builder()
                        .code(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.OK)
                        .message("게시글을 찾을 수 없습니다.")
                        .count(0)
                        .result(null)
                        .build();
            }
        } else if (hashTag != null) {
            Optional<HashTag> hashTagOpt = hashTagRepository.findByName(hashTag);

            if (hashTagOpt.isPresent()) {
                List<PostHashTag> postHashTags = postHashTagRepository.findAllByHashTag(hashTagOpt.get());

                if (!postHashTags.isEmpty()) {
                    List<PostResponse> postResponses = new ArrayList<>();

                    for (PostHashTag postHashTag : postHashTags) {
                        Post post = postHashTag.getPost();
                        PostResponse postResponse = new PostResponse()
                                .toDto(post);

                        postResponse.setHashTags(hashTagService.findHashTags(post));
                        postResponses.add(postResponse);
                    }

                    basicResponse = BasicResponse.builder()
                            .code(HttpStatus.OK.value())
                            .httpStatus(HttpStatus.OK)
                            .message("게시글을 정상적으로 찾았습니다.")
                            .count(postResponses.size())
                            .result(postResponses)
                            .build();
                } else {
                    basicResponse = BasicResponse.builder()
                            .code(HttpStatus.OK.value())
                            .httpStatus(HttpStatus.OK)
                            .message("게시글을 찾을 수 없습니다.")
                            .count(0)
                            .result(null)
                            .build();
                }
            } else {
                basicResponse = BasicResponse.builder()
                        .code(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.OK)
                        .message("헤시태그를 찾을 수 없습니다.")
                        .count(0)
                        .result(null)
                        .build();
            }
        } else {
            List<PostResponse> postResponses = new ArrayList<>();
            List<Post> posts = postRepository.findAll();

            if (!posts.isEmpty()) {
                for (Post post : posts) {
                    PostResponse postResponse = new PostResponse()
                            .toDto(post);

                    postResponse.setHashTags(hashTagService.findHashTags(post));
                    postResponses.add(postResponse);
                }

                 basicResponse = BasicResponse.builder()
                        .code(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.OK)
                        .message("게시글을 정상적으로 찾았습니다.")
                        .count(postResponses.size())
                        .result(postResponses)
                        .build();
            } else {
                basicResponse = BasicResponse.builder()
                        .code(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.OK)
                        .message("게시물을 찾을 수 없습니다..")
                        .count(0)
                        .result(null)
                        .build();
            }
        }
        
        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    @Override
    public ResponseEntity<BasicResponse> addPost(PostRequest postRequest, List<MultipartFile> multipartFiles) throws IOException {
        BasicResponse basicResponse;

        if (postRequest.getTitle().equals("") || postRequest.getContent().equals("")) {
            basicResponse = BasicResponse.builder()
                    .code(HttpStatus.OK.value())
                    .httpStatus(HttpStatus.OK)
                    .message("제목 또는 내용이 비어있습니다.")
                    .build();

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        } else {
            Optional<Member> memberOpt = memberRepository.findById(postRequest.getMemberId());

            if (memberOpt.isPresent()) {
                List<Image> images = imageService.saveImage(multipartFiles);
                Member member = memberOpt.get();

                Post post = Post.builder()
                        .id(null)
                        .title(postRequest.getTitle())
                        .content(postRequest.getContent())
                        .view(0)
                        .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                        .member(member)
                        .build();

                postRepository.save(post);

                List<PostHashTag> hashTags = hashTagService.checkHashTag(post, postRequest.getHashTag());

                post.setPostHashTags(hashTags);
                post.setImages(images);
                member.getPosts().add(post);

                basicResponse = BasicResponse.builder()
                        .code(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.OK)
                        .message("게시물 생성을 정상적으로 완료하였습니다.")
                        .count(1)
                        .result(post)
                        .build();

                return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
            } else {
                basicResponse = BasicResponse.builder()
                        .code(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.OK)
                        .message("작성자를 찾을 수 없습니다.")
                        .count(0)
                        .result(null)
                        .build();

                return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
            }
        }
    }

    @Override
    public ResponseEntity<BasicResponse> editPost(PostRequest postRequest, List<MultipartFile> multipartFiles) throws IOException {
        BasicResponse basicResponse;
        Optional<Post> postOpt = postRepository.findById(postRequest.getPostId());

        if (postRequest.getPostId() == null || postOpt.isEmpty()) {
            basicResponse = BasicResponse.builder()
                    .code(HttpStatus.OK.value())
                    .httpStatus(HttpStatus.OK)
                    .message("수정할 게시글을 찾을 수 없습니다.")
                    .count(0)
                    .result(null)
                    .build();

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        } else {
            Post post = postOpt.get();
            postHashTagRepository.deleteAllByPost(post);
            imageService.deleteImage(post);

            List<PostHashTag> hashTags = hashTagService.checkHashTag(post, postRequest.getHashTag());
            List<Image> images = imageService.saveImage(multipartFiles);

            post.setTitle(postRequest.getTitle());
            post.setContent(postRequest.getContent());
            post.setPostHashTags(hashTags);
            post.setImages(images);

            basicResponse = BasicResponse.builder()
                    .code(HttpStatus.OK.value())
                    .httpStatus(HttpStatus.OK)
                    .message("게시글이 정상적으로 수정되었습니다.")
                    .count(1)
                    .result(post)
                    .build();

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }
    }

    @Override
    public ResponseEntity<BasicResponse> deletePost(Long id) {
        BasicResponse basicResponse;
        Optional<Post> postOpt = postRepository.findById(id);

        if (postOpt.isPresent()) {
            Post post = postOpt.get();
            Member member = post.getMember();
            List<Post> posts = member.getPosts();

            posts.removeIf(memberPost -> memberPost.equals(post));
            member.setPosts(posts);

            postHashTagRepository.deleteAllByPost(post);
            hashTagService.deleteHashTag(post);
            imageService.deleteImage(post);
            postRepository.delete(post);

            basicResponse = BasicResponse.builder()
                    .code(HttpStatus.OK.value())
                    .httpStatus(HttpStatus.OK)
                    .message("게시글이 정상적으로 삭제되었습니다.")
                    .count(0)
                    .result(null)
                    .build();
        } else {
            basicResponse = BasicResponse.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message("삭제할 게시글을 찾을 수 없습니다.")
                    .count(0)
                    .result(null)
                    .build();
        }

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }
    
}
