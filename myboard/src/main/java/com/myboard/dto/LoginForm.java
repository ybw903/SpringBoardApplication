package com.myboard.dto;

import com.myboard.domain.User;
import com.myboard.domain.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class LoginForm {
    private String email;
    private String password;
}
