package com.community.controllers.users;

import com.community.config.JwtUtil;
import com.community.domain.AuthenticationRequest;

import com.community.domain.User;
import com.community.dto.RequestUserDto;
import com.community.service.JpaUserDetailsService;
import com.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Controller
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JpaUserDetailsService userDetailService;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager, JpaUserDetailsService userDetailService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userDetailService = userDetailService;
        this.jwtUtil = jwtUtil;
    }




    @GetMapping("/users/new")
    private String createForm(Model model) {
        model.addAttribute("user", new RequestUserDto());
        return "users/createUserForm";
    }

    @PostMapping("/users/new")
    public String create(RequestUserDto requestUserDto){
        try {
           userService.join(requestUserDto);
           return "redirect:/";

        } catch (Exception e) { return "users/createUserForm"; }
    }

    @GetMapping("/login")
    public String loginForm() {
        return "loginForm";
    }


    /*TODO 로그인 처리*/
    @PostMapping("/login")
    public String authenticate(@RequestParam Map<String, String> body, Model model) {
        log.info("username() = " + body.get("username"));
        log.info("password() = " + body.get("password"));
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(body.get("username"), body.get("password")));
        } catch (BadCredentialsException ex) {
            throw new RuntimeException("name or password is incorrect");
        }

        UserDetails userDetails = userDetailService.loadUserByUsername(body.get("username"));

        String token = jwtUtil.generateToken(userDetails);
        model.addAttribute("token", token);
        return "redirect:/";

    }
}
