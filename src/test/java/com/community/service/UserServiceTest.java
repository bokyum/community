package com.community.service;

import com.community.domain.User;
import com.community.dto.RequestUserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    DirectUserRepository directUserRepository;

    @Test
    void 정상회원가입() {
        //given
        RequestUserDto input = new RequestUserDto("마르코12",
                "aser12@naver.com",
                "QHrua123!"
        );

        //when
        Long saveId = userService.join(input);
        User user = directUserRepository.findById(saveId);

        //then
        assertThat(user.getUsername()).isEqualTo("마르코12");
        assertThat(user.getEmail()).isEqualTo("aser12@naver.com");
        assertThat(user.getPassword()).isEqualTo("QHrua123!");
    }
    @Test
    void 비정상가입() {
        RequestUserDto requestUserDto = new RequestUserDto("!@하이@", "123123@1qweq"
                , "qweqwe12");

        Assertions.assertThrows(IllegalArgumentException.class, ()->
        {
            userService.join(requestUserDto);
        });
    }
}