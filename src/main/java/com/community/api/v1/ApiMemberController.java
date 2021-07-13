package com.community.api.v1;

import com.community.domain.User;
import com.community.dto.RequestUserDto;
import com.community.dto.ResponseUserDto;
import com.community.dto.ReturnUserDto;
import com.community.service.UserService;
import com.community.validations.EmailValidation;
import com.community.validations.NameValidation;
import com.community.validations.ValidationCheck;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/v1/api")
public class ApiMemberController {


    private final UserService userService;

    private final ValidationCheck validation;

    @Autowired
    public ApiMemberController(UserService userService, ValidationCheck validation) {
        this.userService = userService;
        this.validation = validation;
    }



    @ApiOperation("모든 유저 검색")
    @GetMapping("/users")
    public ResponseEntity<ReturnUserDto> allUsers() {
        List<User> users = userService.findAllUsers();
        List<ResponseUserDto> result =
                users.stream().
                        map((user) ->
                new ResponseUserDto(user.getUsername(), user.getEmail(), user.getUmjjals()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(new ReturnUserDto(result, null), HttpStatus.OK);
    }

    @ApiOperation("유저 데이터 생성")
    @PostMapping("/users")
    public ResponseEntity<ReturnUserDto> createMember(@RequestBody RequestUserDto requestUserDto) {
        try {
            validation.isValidName(requestUserDto.getUsername());
            validation.isValidPassword(requestUserDto.getPassword());
            validation.isValidEmail(requestUserDto.getEmail());
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ReturnUserDto(null, e.getMessage()),
                    HttpStatus.CONFLICT);
        }

        Long id = userService.join(requestUserDto);

        return new ResponseEntity<>(new ReturnUserDto(id, null), HttpStatus.CREATED);

    }

    @ApiOperation("특정 유저 검색")
    @GetMapping("/users/{id}")
    public ResponseEntity<ReturnUserDto> findMemberById(@PathVariable Long id) {
            User user = userService.findUserById(id);
            if(user == null)
                return new ResponseEntity(new ReturnUserDto<>(null, "존재하지 않는 유저입니다."),
                        HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(new ReturnUserDto(user, null),
                   HttpStatus.OK);
    }




    @ApiOperation("업데이트 유저 정보")
    @PatchMapping("/users/{id}")
    public ResponseEntity<ReturnUserDto> updateMember(@PathVariable Long id,
                                                      @RequestBody RequestUserDto requestUserDto) {
        try {
            validation.isValidNameWhenUpdate(requestUserDto.getUsername());
            validation.isValidEmailWhenUpdate(requestUserDto.getEmail());
            validation.isValidPassword(requestUserDto.getPassword());
        } catch (Exception e) {
            return new ResponseEntity<>(new ReturnUserDto(null, e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
        User user = userService.updateUserById(id, requestUserDto);
        ResponseUserDto responseUserDto =
                new ResponseUserDto(user.getUsername(), user.getEmail(), user.getUmjjals());

        return new ResponseEntity<>(new ReturnUserDto(responseUserDto, null), HttpStatus.OK);
    }

    @ApiOperation("유저 정보 삭제")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<ReturnUserDto> deleteMember(@PathVariable Long id) {
        User user = userService.findUserById(id);
        if(user == null) {
            return new ResponseEntity<>(new ReturnUserDto(null, "존재하지 않는 회원입니다."), HttpStatus.BAD_REQUEST);
        }
        userService.deleteUser(user);
        return new ResponseEntity<>(new ReturnUserDto("success", null), HttpStatus.OK);

    }


}
