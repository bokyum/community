package com.community.api.v1;

import com.community.domain.Member;
import com.community.dto.RequestMemberDto;
import com.community.dto.ResponseMemberDto;
import com.community.service.MemberService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/v1/api")
public class MemberController {

    @Autowired
    MemberService memberService;


    @ApiOperation("모든 유저 검색")
    @GetMapping("/users")
    public ResponseEntity<ReturnMemberDto> allUsers() {
        List<Member> members = memberService.findAllMembers();
        List<ResponseMemberDto> result =
                members.stream().
                        map((member) ->
                new ResponseMemberDto(member.getName(), member.getEmail(), member.getPosts()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(new ReturnMemberDto(result, null), HttpStatus.OK);
    }

    @ApiOperation("유저 데이터 생성")
    @PostMapping("/users")
    public ResponseEntity<ReturnMemberDto> createMember(@RequestBody RequestMemberDto requestMemberDto) {
        try {
            Long id = memberService.join(requestMemberDto);

            return new ResponseEntity<>(new ReturnMemberDto(id, null), HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ReturnMemberDto(null, e.getMessage()),
                    HttpStatus.CONFLICT);
        }
    }

    @ApiOperation("특정 유저 검색")
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




    @ApiOperation("업데이트 유저 정보")
    @PatchMapping("/users/{id}")
    public ResponseEntity<ReturnMemberDto> updateMember(@PathVariable Long id,
                                                        @RequestBody RequestMemberDto requestMemberDto) {
        try {
            memberService.updateMemberById(id, requestMemberDto);
            Member member = memberService.findMemberById(id);
            ResponseMemberDto responseMemberDto =
                    new ResponseMemberDto(member.getName(), member.getEmail(), member.getPosts());

            return new ResponseEntity<>(
                    new ReturnMemberDto(responseMemberDto, null),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(new ReturnMemberDto(null, e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation("유저 정보 삭제")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<ReturnMemberDto> deleteMember(@PathVariable Long id) {
        try {
            memberService.deleteMemberById(id);
            return new ResponseEntity<>(new ReturnMemberDto("success", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ReturnMemberDto(null, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
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
