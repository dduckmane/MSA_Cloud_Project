package com.example.userservice.config;

import com.example.userservice.domain.repository.MemberRedisRepository;
import com.example.userservice.common.filter.JwtAuthenticationFilter;
import com.example.userservice.common.utils.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final CorsConfig corsConfig;
    private final MemberRedisRepository memberRepository;
    private final TokenUtils tokenUtils;
    private final ObjectMapper om;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().cors();
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()

                .apply(new MyCustomDsl(om, tokenUtils))

                .and()
                .authorizeRequests()

//                .requestMatchers("/actuator/**")
//                .permitAll()
//
//                // 아임포트 웹훅(결제내역 동기화)
//                .antMatchers("/api/v1/user/payment/webhook")
//                .permitAll()
//
//                .antMatchers("/api/v1/user/**")
//                .access("hasRole('ROLE_USER')or hasRole('ROLE_MANAGER')or hasRole('ROLE_ADMIN')")
//
//                .antMatchers("/api/v1/manager/**")
//                .access("hasRole('ROLE_MANAGER')or hasRole('ROLE_ADMIN')")
//
//                .antMatchers("/api/v1/admin/**")
//                .access("hasRole('ROLE_ADMIN')")

                .anyRequest().permitAll()

        ;
        return httpSecurity.build();

    }
    @RequiredArgsConstructor
    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        private final ObjectMapper om;
        private final TokenUtils tokenUtils;
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http
                    .addFilter(corsConfig.corsFilter())
                    .addFilter(new JwtAuthenticationFilter(authenticationManager, tokenUtils, memberRepository))
            ;

        }
    }
}