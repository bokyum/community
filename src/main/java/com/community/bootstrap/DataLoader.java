package com.community.bootstrap;

import com.community.domain.User;
import com.community.domain.Role;

import com.community.repository.RoleRepository;
import com.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;



    @Override
    public void run(String... args) throws Exception {
        loadSecurityData();
        loadUmjjalData();
    }


    private void loadSecurityData() {
        Role adminRole = roleRepository.save(Role.builder().name("ADMIN").build());
        Role customerRole = roleRepository.save(Role.builder().name("CUSTOMER").build());
        Role userRole = roleRepository.save(Role.builder().name("USER").build());

        roleRepository.saveAll(Arrays.asList(adminRole, customerRole, userRole));

        userRepository.save(User.builder()
        .username("admin")
        .password(passwordEncoder.encode("password"))
        .role(adminRole).build());

    }

    private void loadUmjjalData() {
    }
}
