package com.myboard.controller;

import com.myboard.dto.LoginForm;
import com.myboard.dto.SignUpForm;
import com.myboard.security.UserSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserSecurityService userSecurityService;

    @GetMapping("/user/login")
    public String login() {
        return "/user/loginForm";
    }

    @GetMapping("/user/sign-up")
    public String signUp() {
        return "/user/signUpForm";
    }

    @PostMapping("/user/sign-up")
    public String signUpUser(SignUpForm signUpForm) {
        userSecurityService.signUpUser(signUpForm);
        return "redirect:/";
    }
}
