package com.codeup.codeupspringblog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringBlogSecurityConfiguration {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests((requests) -> requests
//                .requestMatchers("/posts/create", "/posts/comment", "/posts/*/edit").authenticated()
//                .requestMatchers("/posts", "/posts/**").permitAll()
//                .requestMatchers("/css/**", "/js/**").permitAll()
//        );
        http.authorizeHttpRequests((requests) -> requests
                .anyRequest().permitAll()
        );
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }
}
