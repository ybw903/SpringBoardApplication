package com.myboard.security;

import com.myboard.domain.User;
import com.myboard.domain.UserRepository;
import com.myboard.dto.SignUpForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional
    public User signUpUser(SignUpForm signUpForm) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        signUpForm.setPassword(passwordEncoder.encode(signUpForm.getPassword()));

        return userRepository.save(signUpForm.toEntity(signUpForm));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("사용자 이메일을 찾을 수 없습니다."));
        return new PrincipalDetails(user);
    }

}