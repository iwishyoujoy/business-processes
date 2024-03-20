package com.litres.bookstore.config;

import com.litres.bookstore.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserServiceImpl userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public WebSecurityConfig(UserServiceImpl userService, BCryptPasswordEncoder bCryptPasswordEncoder1) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder1;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(requests -> requests
                .antMatchers("/api/authors/registration", "/api/readers/registration", "/api/login").permitAll()
                .antMatchers("/api/authors/**").hasRole("AUTHOR")
                .antMatchers("/api/readers/**").hasRole("READER")).authorizeRequests(requests -> requests.antMatchers("/swagger-ui/**").permitAll()).authorizeRequests(requests -> requests.antMatchers("/v3/api-docs/**").permitAll()
            .anyRequest().authenticated())
            .cors(withDefaults())
            .csrf(csrf -> csrf.disable())
            .httpBasic(withDefaults());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }
}