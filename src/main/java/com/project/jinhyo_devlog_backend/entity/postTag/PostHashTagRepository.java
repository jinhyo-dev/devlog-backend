package com.project.jinhyo_devlog_backend.entity.postTag;

import com.project.jinhyo_devlog_backend.entity.hashTag.HashTag;
import com.project.jinhyo_devlog_backend.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostHashTagRepository extends JpaRepository<PostHashTag, Long> {

    List<PostHashTag> findAllByHashTag(HashTag hashTag);

    void deleteAllByPost(Post post);

}
