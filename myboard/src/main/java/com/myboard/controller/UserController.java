package com.myboard.controller;

import com.myboard.dto.SignUpForm;
import com.myboard.exception.DuplicateUserEmailException;
import com.myboard.security.UserSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserSecurityService userSecurityService;

    @GetMapping("/user/login")
    public String login() {
        return "/user/loginForm";
    }

    @GetMapping("/user/sign-up")
    public String signUp(Model model) {
        model.addAttribute("signUpForm", new SignUpForm());
        return "/user/signUpForm";
    }

    @PostMapping("/user/sign-up")
    public String signUpUser(@Valid SignUpForm signUpForm, BindingResult result) {
        if(result.hasErrors()) {
            return "/user/signUpForm";
        }
        try {
            userSecurityService.signUpUser(signUpForm);
        } catch (DuplicateUserEmailException e) {
            result.rejectValue("email","email",e.getMessage());
            return "/user/signUpForm";
        }

        return "redirect:/";
    }

}
