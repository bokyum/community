package com.community.api.v1.controller.users;


import com.community.api.v1.dto.ResponseDto;
import com.community.api.v1.dto.request.auth.JoinRequest;
import com.community.api.v1.dto.request.auth.LoginRequest;
import com.community.api.v1.dto.response.auth.JwtResponse;
import com.community.api.v1.service.UserService;
import com.community.domain.ERole;
import com.community.domain.Role;
import com.community.domain.User;
import com.community.domain.repository.RoleRepository;
import com.community.domain.security.UserDetailsImpl;
import com.community.security.jwt.JwtUtils;
import com.community.validation.AuthValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final AuthValidation authValidation;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        authValidation.isValidName(loginRequest.getUsername());
        authValidation.isValidPassword(loginRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        JwtResponse jwtResponse = new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
        return ResponseEntity.ok(new ResponseDto<>(jwtResponse, null));
    }

    @PostMapping("/join")
    public ResponseEntity<?> registerUser(@Valid @RequestBody JoinRequest joinRequest) {
        //System.out.println(joinRequest.getPassword());
        authValidation.isValidNameJoin(joinRequest.getUsername());
        authValidation.isValidEmailJoin(joinRequest.getEmail());
        authValidation.isValidPasswordJoin(joinRequest.getPassword());


        User user = createUser(joinRequest);
        userService.join(user);

        return ResponseEntity.ok(new ResponseDto<>("정상적으로 회원가입되었습니다", null));
    }






    private User createUser(JoinRequest joinRequest) {
        User user = new User(joinRequest.getUsername(),
                joinRequest.getEmail(),
                encoder.encode(joinRequest.getPassword()));

        Set<String> strRoles = joinRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        return user;
    }
}
