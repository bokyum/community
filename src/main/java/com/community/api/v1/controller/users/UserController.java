package com.community.api.v1.controller.users;

import com.community.api.v1.dto.ResponseDto;
import com.community.api.v1.dto.request.users.UserInfoRequest;
import com.community.api.v1.service.UserService;
import com.community.domain.User;
import com.community.domain.repository.UserRepository;
import com.community.domain.repository.querydsl.UserRepositorySupport;
import com.community.validation.AuthValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder encoder;
    private final AuthValidation authValidation;

    //TODO 토큰과 아이디 비교하여 다른 사용자가 회원 정보 수정 못하게 막아야함
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> userDetails(@PathVariable Long id) {
        User user = userService.userDetails(id);
        if(user == null)
            throw new IllegalArgumentException("존재하지 않는 회원 정보입니다.");

        return new ResponseEntity<>(new ResponseDto(user, null), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto> updateUser(@PathVariable Long id, @RequestBody UserInfoRequest req) {
        authValidation.isValidNameWhenUpdate(id, req.getUsername());
        authValidation.isValidEmailWhenUpdate(id, req.getEmail());
        authValidation.isValidPassword(req.getPassword());

        req.setPassword(encoder.encode(req.getPassword()));
        User user = userService.updateUserInfo(id, req);

        return new ResponseEntity<>(new ResponseDto(user, null), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(new ResponseDto("정상적으로 탈퇴되었습니다.", null), HttpStatus.OK);
    }



}
