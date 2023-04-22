package com.example.fintech.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ProjectSecurityConfig {


    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        //custom security configuration
        http.authorizeHttpRequests() //.anyRequest().denyAll() or .anyRequest().permitAll() can be used instead of requestMatchers
                .requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated()
                .requestMatchers("/notices", "/contact").permitAll()
                .and().formLogin().and().httpBasic();
        return http.build();
    }

    @Bean //First Approach
    public InMemoryUserDetailsManager userDetailsManager(){

        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("12345")
                .authorities("admin")
                .build();
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("123user")
                .authorities("read")
                .build();
        return new InMemoryUserDetailsManager(admin, user);
    }

    /*@Bean //Second Approach
    public InMemoryUserDetailsManager userDetailsManager2(){

        UserDetails admin = User.withUsername("admin")
                .password("12345")
                .authorities("admin")
                .build();
        UserDetails user = User.withUsername("user")
                .password("12345user")
                .authorities("read")
                .build();
        return new InMemoryUserDetailsManager(admin, user);
    }
    @Bean //needed only for second approach
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }*/
}

