package com.community.api;

import com.community.dto.JoinMemberDto;
import com.community.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @Autowired
    MemberService memberService;

    @PostMapping("/users/new")
    public returnMemberDto create(@RequestBody JoinMemberDto joinMemberDto) {
        try {
            Long id = memberService.join(joinMemberDto);
            return new returnMemberDto(id, null);

        } catch (Exception e) {
            return new returnMemberDto(null, e.toString());
        }
    }



    @Data
    private class returnMemberDto{
        Long id;
        String error;

        public returnMemberDto(Long id, String error) {
            this.id = id;
            this.error = error;
        }
    }

}
