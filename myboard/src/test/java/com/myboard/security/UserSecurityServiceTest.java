package com.myboard.security;

import com.myboard.domain.User;
import com.myboard.domain.UserRepository;
import com.myboard.dto.SignUpForm;
import com.myboard.exception.DuplicateUserEmailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserSecurityServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserSecurityService userSecurityService;

    @DisplayName("회원가입에 성공하는 경우")
    @Test
    void signUpUserSuccessful() {
        final SignUpForm signUpForm = new SignUpForm("test@gmail.com","1234");
        final User mockUser = mock(User.class);
        given(userRepository.save(any(User.class))).willReturn(mockUser);

        User savedUser = userSecurityService.signUpUser(signUpForm);

        verify(userRepository).save(any(User.class));
    }
    

    @DisplayName("회원가입 요청에 사용된 메일이 이미 등록되어 실패하는 경우")
    @Test
    void signUpUserFailureByDuplicateUserEmailException() {
        final SignUpForm signUpForm = new SignUpForm("test@gmail.com","1234");
        final User signedUpUser = mock(User.class);
        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(signedUpUser));

        assertThrows(DuplicateUserEmailException.class,()->userSecurityService.signUpUser(signUpForm));
        verify(userRepository).findByEmail(anyString());
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("사용자 이메일을 찾을 수 없습니다."));
        return new PrincipalDetails(user);
    }
    
    @DisplayName("loadUserByUsername 테스트 ")
    @Test
    void loadUserByUsernameTest() {
        final User user = mock(User.class);
        when(user.getEmail()).thenReturn("test@gmail.com");
        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(user));

        PrincipalDetails principalDetails = (PrincipalDetails) userSecurityService.loadUserByUsername("test@gmail.com");

        assertThat(principalDetails.getUsername()).isEqualTo(user.getEmail());
        verify(userRepository).findByEmail(anyString());
    }

    @Test
    void loadUserByUsernameFailure() {
        given(userRepository.findByEmail(anyString())).willReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, ()->userSecurityService.loadUserByUsername(""));
    }

}