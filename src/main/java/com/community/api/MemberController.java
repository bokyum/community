package com.community.api;

import com.community.domain.Member;
import com.community.dto.RequestMemberDto;
import com.community.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MemberController {

    @Autowired
    MemberService memberService;


    @GetMapping("/users")
    public ResponseEntity<ReturnMemberDto> allUsers() {
        List<Member> allUsers = memberService.findAllMembers();
        return new ResponseEntity<>(new ReturnMemberDto(allUsers, null), HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<ReturnMemberDto> createMember(@RequestBody RequestMemberDto requestMemberDto) {
        try {
            Long id = memberService.join(requestMemberDto);

            return new ResponseEntity<>(new ReturnMemberDto(id, null), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ReturnMemberDto(null, e.getMessage()),
                    HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ReturnMemberDto> findMemberById(@PathVariable Long id) {
        try {
            Member member = memberService.findMemberById(id);
            return new ResponseEntity<>(new ReturnMemberDto(member, null),
                    HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity(new ReturnMemberDto<>(null, e.getMessage()),
                    HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/users/{name}")
    public ResponseEntity<ReturnMemberDto> findMemberByName(@PathVariable String name){
        try {
            List<Member> members = memberService.findMemberByName(name);
            return new ResponseEntity<>(new ReturnMemberDto(members, null), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ReturnMemberDto(null, e.getMessage()), HttpStatus.NO_CONTENT);
        }
    }


    //TODO: Member update 관리
    @PostMapping("/users/{id}")
    public ResponseEntity<ReturnMemberDto> updateMember(@RequestBody RequestMemberDto requestMemberDto) {
        return null;
    }



    @Data
    private class ReturnMemberDto<T>{
        T data;
        String error;

        public ReturnMemberDto(T data, String error) {
            this.data = data;
            this.error = error;
        }
    }

}
