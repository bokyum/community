package com.community.api.v1.controller.posts;

import com.community.api.v1.dto.ResponseDto;
import com.community.api.v1.dto.request.post.PostRequest;
import com.community.api.v1.dto.request.post.PostResponse;
import com.community.api.v1.service.PostService;
import com.community.api.v1.service.UserService;
import com.community.domain.Post;
import com.community.domain.Tag;
import com.community.domain.User;
import com.community.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {

    private final JwtUtils jwtUtils;
    private final PostService postService;
    private final UserService userService;

    @PostMapping("/save")
    public ResponseEntity<ResponseDto> savePost(@RequestBody PostRequest form,
                                                    HttpServletRequest request) {
        User user = getUser(form, request);
        Post post = postService.savePost(user, form);

        PostResponse postResponse = PostResponse.builder()
                .username(post.getUser().getUsername())
                .title((post.getTitle()))
                .content((post.getContent()))
                .createdAt(post.getCreatedAt())
                .lastModifiedDate(post.getLastModifiedDate())
                .build();

        List<Tag> tag = post.getTag();
        for (Tag tag1 : tag) {
            postResponse.getTags().add(tag1.getKeyword().getWord());
        }
        return new ResponseEntity<>(new ResponseDto(postResponse, null), HttpStatus.OK);

    }

    private User getUser(PostRequest form, HttpServletRequest request) {
        String token = parseJwt(request);
        String usernameWithJWT = jwtUtils.getUserNameFromJwtToken(token);
        if(usernameWithJWT.isEmpty())
            throw new IllegalArgumentException("로그인이 필요한 작업입니다.");
        User user = userService.findUserByUsername(usernameWithJWT);
        if(user == null)
            throw new IllegalArgumentException("로그인이 필요한 작업입니다.");
        if(!usernameWithJWT.equals(form.getUsername()))
             throw new IllegalArgumentException("접근 권한이 없습니다.");
        return user;
    }

//    @PatchMapping("/{id}")
//    public ResponseEntity<ResponseDto> editPost(@PathVariable Long postId,
//                                                @RequestBody PostRequest form,
//                                                HttpServletRequest request) {
//        User user = getUser(request);
//
//    }



    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }
}
