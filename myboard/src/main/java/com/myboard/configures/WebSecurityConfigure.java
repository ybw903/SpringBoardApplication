package com.myboard.configures;

import com.myboard.security.UserSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter {

    private final UserSecurityService userSecurityService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/h2-console/**", "/static/img/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .csrf()
                .disable()
        .authorizeRequests()
                .antMatchers("/posts/new", "/posts/**/edit","/posts/**/delete").hasRole("USER")
                .antMatchers("/**").permitAll()
        .and()
                .formLogin()
                .loginPage("/user/login")
                .loginProcessingUrl("/user/auth")
                .defaultSuccessUrl("/",true)
                .permitAll()
        .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
        .and()
                .exceptionHandling().accessDeniedPage("/login/error");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
    }
}
