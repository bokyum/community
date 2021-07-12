package com.community.controllers.members;

import com.community.domain.Member;
import com.community.dto.RequestMemberDto;
import com.community.dto.ReturnMemberDto;
import com.community.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members")
    private String memberList(Model model) {
        List<Member> members = memberService.findAllMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

    @GetMapping("/members/new")
    private String createForm(Model model) {
        model.addAttribute("requestMemberDto", new RequestMemberDto());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(RequestMemberDto requestMemberDto){
        try {
           memberService.join(requestMemberDto);
           return "redirect:/";

        } catch (Exception e) { return "members/createMemberForm"; }
    }
}
