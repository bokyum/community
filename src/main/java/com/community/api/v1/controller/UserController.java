package com.community.api.v1.controller;


import com.community.SessionConst;
import com.community.api.v1.dto.AuthForm;
import com.community.api.v1.service.UserService;
import com.community.domain.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> userDetail(@PathVariable Long id) {
        User user = userService.findUserById(id);

        if(user == null) {
            new ResponseEntity<>(new ResponseDto<>(null, "존재하지 않는 회원입니다."), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new ResponseDto(user, ""), HttpStatus.OK);

    }
    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@Valid @RequestBody AuthForm form,
                                             BindingResult bindingResult,
                                             @RequestParam(defaultValue = "/") String redirectURL,
                                             HttpServletRequest request,
                                             HttpServletResponse response) {

        if(bindingResult.hasErrors()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(redirectURL));
            return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
        }
        User loginUser = userService.login(form);
        log.info("login {}", loginUser);
        if(loginUser == null) {
            return  new ResponseEntity<>(new ResponseDto(null, "아이디 또는 비밀번호가 맞지 않습니다"),
                    HttpStatus.BAD_REQUEST);

        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginUser);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/"));
        return new ResponseEntity(new ResponseDto<>(loginUser, null),headers, HttpStatus.OK);
    }

    @PostMapping("/join")
    public ResponseEntity<ResponseDto> join(@Valid @RequestBody AuthForm form){
        User joinUser = userService.join(form);
        return new ResponseEntity<>(new ResponseDto(joinUser, null), HttpStatus.OK);
    }

    @Data
    private static class ResponseDto<T> {

        T data;
        String error;

        public ResponseDto(T data, String error) {
            this.data = data;
            this.error = error;
        }
    }
}
