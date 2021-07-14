package com.myboard.dto;

import com.myboard.domain.User;
import com.myboard.domain.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SignUpForm {
    private String email;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$",
            message = "비밀번호는 '숫자', '문자' 무조건 1개 이상, '최소 8자에서 최대 20자' 허용합니다.")
    private String password;

    public User toEntity(SignUpForm signUpForm) {
        return User.builder()
                .email(signUpForm.getEmail())
                .password(signUpForm.getPassword())
                .userRole(UserRole.USER)
                .build();
    }
}
