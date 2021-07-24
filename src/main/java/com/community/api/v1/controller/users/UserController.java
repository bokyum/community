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
        String token = parseJwt(request);
        String usernameWithJWT = jwtUtils.getUserNameFromJwtToken(token);
        User user = userService.userDetails(id);
        if(!user.getUsername().equals(usernameWithJWT))
            throw new IllegalArgumentException("접근권한이 없습니다.");
        if(user == null)
            throw new IllegalArgumentException("존재하지 않는 회원 정보입니다.");

        return new ResponseEntity<>(new ResponseDto(user, null), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto> updateUser(@PathVariable Long id, @RequestBody UserInfoRequest req,
                                                  HttpServletRequest request) {
        String token = parseJwt(request);
        String usernameWithJWT = jwtUtils.getUserNameFromJwtToken(token);
        User user = userService.userDetails(id);
        if(!user.getUsername().equals(usernameWithJWT))
            throw new IllegalArgumentException("접근권한이 없습니다.");


        authValidation.isValidNameWhenUpdate(id, req.getUsername());
        authValidation.isValidEmailWhenUpdate(id, req.getEmail());
        authValidation.isValidPassword(req.getPassword());

        req.setPassword(encoder.encode(req.getPassword()));
        User updatedUser = userService.updateUserInfo(id, req);

        return new ResponseEntity<>(new ResponseDto(updatedUser, null), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteUser(@PathVariable Long id, HttpServletRequest request) {
        String token = parseJwt(request);
        String usernameWithJWT = jwtUtils.getUserNameFromJwtToken(token);
        User user = userService.userDetails(id);
        if(!user.getUsername().equals(usernameWithJWT))
            throw new IllegalArgumentException("접근권한이 없습니다.");

        userService.deleteUser(id);
        return new ResponseEntity<>(new ResponseDto("정상적으로 탈퇴되었습니다.", null), HttpStatus.OK);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }



}
