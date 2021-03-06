package com.community.api.v1.controller.users;

import com.community.api.v1.dto.ResponseDto;
import com.community.api.v1.dto.request.users.UserInfoRequest;
import com.community.api.v1.service.UserService;
import com.community.domain.User;
import com.community.domain.repository.UserRepository;
import com.community.domain.repository.querydsl.UserRepositorySupport;
import com.community.security.jwt.JwtUtils;
import com.community.validation.AuthValidation;
import javassist.tools.web.BadHttpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder encoder;
    private final AuthValidation authValidation;
    private final JwtUtils jwtUtils;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> userDetails(@PathVariable Long id, HttpServletRequest request) {
        User user = getUser(id, request);

        return new ResponseEntity<>(new ResponseDto(user, null), HttpStatus.OK);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto> updateUser(@PathVariable Long id, @RequestBody UserInfoRequest req,
                                                  HttpServletRequest request) {
        User user = getUser(id, request);

        authValidation.isValidNameWhenUpdate(id, req.getUsername());
        authValidation.isValidEmailWhenUpdate(id, req.getEmail());
        authValidation.isValidPassword(req.getPassword());

        req.setPassword(encoder.encode(req.getPassword()));
        User updatedUser = userService.updateUserInfo(user, req);

        return new ResponseEntity<>(new ResponseDto(updatedUser, null), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteUser(@PathVariable Long id, HttpServletRequest request) {
        User user = getUser(id, request);

        userService.deleteUser(user);
        return new ResponseEntity<>(new ResponseDto("??????????????? ?????????????????????.", null), HttpStatus.OK);
    }

    private User getUser(Long id, HttpServletRequest request) {
        String token = parseJwt(request);
        String usernameWithJWT = jwtUtils.getUserNameFromJwtToken(token);
        if(usernameWithJWT.isEmpty())
            throw new IllegalArgumentException("??????????????? ????????????.");
        User user = userService.userDetails(id);
        if(!user.getUsername().equals(usernameWithJWT))
            throw new IllegalArgumentException("??????????????? ????????????.");
        if(user == null)
            throw new IllegalArgumentException("???????????? ?????? ?????? ???????????????.");
        return user;
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }



}
