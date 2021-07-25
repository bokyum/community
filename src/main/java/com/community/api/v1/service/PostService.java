package com.community.api.v1.service;

import com.community.api.v1.dto.request.post.PostRequest;
import com.community.domain.Keyword;
import com.community.domain.Post;
import com.community.domain.Tag;
import com.community.domain.User;
import com.community.domain.repository.KeywordRepository;
import com.community.domain.repository.PostRepository;
import com.community.domain.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final KeywordRepository keywordRepository;
    private final TagRepository tagRepository;

    @Transactional(readOnly = false)
    public Post savePost(User user, PostRequest form) {
        Post post = new Post(user, form.getTitle(), form.getContent());
        postRepository.save(post);

        List<String> words = form.getTags();
        List<Tag> tags = new ArrayList<>();
        for (String word : words) {
            Tag tag = new Tag();
            tag.setPost(post);
            Optional<Keyword> keyword = keywordRepository.findByWord(word);
            Keyword keyword1;
            if(keyword.isEmpty()) {
                keyword1 = new Keyword(word);
                tag.setKeyword(keyword1);
                keyword1.addTag(tag);
                keywordRepository.save(keyword1);
                tagRepository.save(tag);

            }
            else {
                keyword1 = keyword.get();
                tag.setKeyword(keyword1);
                List<Tag> tagList = keyword1.getTag();
                tagList.add(tag);
                keyword1.setTag(tagList);
                tagRepository.save(tag);
            }

            tags.add(tag);
        }
        post.setTag(tags);

        return post;
    }


}
