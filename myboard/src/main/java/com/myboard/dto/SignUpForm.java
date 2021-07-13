package com.myboard.dto;

import com.myboard.domain.User;
import com.myboard.domain.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpForm {
    private String email;
    private String password;

    public User toEntity(SignUpForm signUpForm) {
        return User.builder()
                .email(signUpForm.getEmail())
                .password(signUpForm.getPassword())
                .userRole(UserRole.USER)
                .build();
    }
}
