package com.community.api.v1;

import com.community.domain.Member;
import com.community.dto.RequestMemberDto;
import com.community.dto.ResponseMemberDto;
import com.community.dto.ReturnMemberDto;
import com.community.service.MemberService;
import com.community.validations.EmailValidation;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


import static com.community.validations.NameValidation.isValidName;
import static com.community.validations.PasswordValidation.isValidPassword;

@RestController
@RequestMapping("/v1/api")
public class ApiMemberController {


    private final MemberService memberService;
    private final EmailValidation emailValidation;


    @Autowired
    public ApiMemberController(MemberService memberService, EmailValidation emailValidation) {
        this.memberService = memberService;
        this.emailValidation = emailValidation;
    }



    @ApiOperation("모든 유저 검색")
    @GetMapping("/members")
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
    @PostMapping("/members")
    public ResponseEntity<ReturnMemberDto> createMember(@RequestBody RequestMemberDto requestMemberDto) {
        try {
            isValidName(requestMemberDto.getName());
            isValidPassword(requestMemberDto.getPassword());
            emailValidation.isValidEmail(requestMemberDto.getEmail());
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ReturnMemberDto(null, e.getMessage()),
                    HttpStatus.CONFLICT);
        }

        Long id = memberService.join(requestMemberDto);

        return new ResponseEntity<>(new ReturnMemberDto(id, null), HttpStatus.CREATED);

    }

    @ApiOperation("특정 유저 검색")
    @GetMapping("/members/{id}")
    public ResponseEntity<ReturnMemberDto> findMemberById(@PathVariable Long id) {
            Member member = memberService.findMemberById(id);
            if(member == null)
                return new ResponseEntity(new ReturnMemberDto<>(null, "존재하지 않는 유저입니다."),
                        HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(new ReturnMemberDto(member, null),
                   HttpStatus.OK);
    }




    @ApiOperation("업데이트 유저 정보")
    @PatchMapping("/members/{id}")
    public ResponseEntity<ReturnMemberDto> updateMember(@PathVariable Long id,
                                                        @RequestBody RequestMemberDto requestMemberDto) {
        try {
            emailValidation.isValidEmailWhenUpdate(requestMemberDto.getEmail());
            isValidPassword(requestMemberDto.getPassword());
            isValidName(requestMemberDto.getName());
        } catch (Exception e) {
            return new ResponseEntity<>(new ReturnMemberDto(null, e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
        Member member = memberService.updateMemberById(id, requestMemberDto);
        ResponseMemberDto responseMemberDto =
                new ResponseMemberDto(member.getName(), member.getEmail(), member.getPosts());

        return new ResponseEntity<>(new ReturnMemberDto(responseMemberDto, null), HttpStatus.OK);
    }

    @ApiOperation("유저 정보 삭제")
    @DeleteMapping("/members/{id}")
    public ResponseEntity<ReturnMemberDto> deleteMember(@PathVariable Long id) {
        Member member = memberService.findMemberById(id);
        if(member == null) {
            return new ResponseEntity<>(new ReturnMemberDto(null, "존재하지 않는 회원입니다."), HttpStatus.BAD_REQUEST);
        }
        memberService.deleteMember(member);
        return new ResponseEntity<>(new ReturnMemberDto("success", null), HttpStatus.OK);

    }


}
