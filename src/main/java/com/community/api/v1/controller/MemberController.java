package com.community.api.v1.controller;

import com.community.api.v1.dto.members.CreateForm;
import com.community.api.v1.dto.members.LoginForm;
import com.community.api.v1.dto.members.MemberDetailForm;
import com.community.api.v1.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1")
@RestController
public class MemberController {

    private final MemberService MemberService;

    @Autowired
    public MemberController(MemberService MemberService) {
        this.MemberService = MemberService;
    }

    @PostMapping("/members")
    public ResponseEntity login(@Validated @RequestBody LoginForm form) {
        if(MemberService.login(form)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/members/new")
    public ResponseEntity<MemberDetailForm>create(@Validated @RequestBody CreateForm form) {
        MemberDetailForm member = MemberService.createMember(form);
        if(member == null)
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        else
            return new ResponseEntity<>(member, HttpStatus.OK);
    }

}

