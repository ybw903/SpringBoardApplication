package com.myboard.dto;

import com.myboard.domain.User;
import com.myboard.domain.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginForm {
    private String email;
    private String password;
}
