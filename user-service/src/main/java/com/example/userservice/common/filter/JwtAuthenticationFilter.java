package com.example.userservice.common.filter;


import com.example.userservice.domain.entity.RedisMember;
import com.example.userservice.domain.entity.UserEntity;
import com.example.userservice.domain.repository.MemberRedisRepository;
import com.example.userservice.common.auth.PrincipalDetails;
import com.example.userservice.common.utils.TokenUtils;
import com.example.userservice.common.utils.Tokens;
import com.example.userservice.web.request.RequestLogin;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.io.IOException;

import static com.example.userservice.common.utils.TokenUtils.*;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    /**
     * 1. 로그인이 진행되는 filter
     * 2. 계정이 잠긴 경우, 로그인 정보가 안 맞는 경우 각 모두 상이한 custom error response 객체를 응답
     * 3. 로그인 성공 시에 jwtToken 과 refreshToken 이 나감
     **/
    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;
    private ObjectMapper om;
    private MemberRedisRepository memberRepository;



    public JwtAuthenticationFilter(
            AuthenticationManager authenticationManager
            ,TokenUtils tokenUtils
            ,MemberRedisRepository memberRepository
    ) {

        this.authenticationManager = authenticationManager;
        this.memberRepository=memberRepository;
        this.tokenUtils = tokenUtils;
    }

    //로그인 시에 실행이 되는 필
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request
            , HttpServletResponse response
    ) throws AuthenticationException {
        log.info("attemptAuthentication 실행");

        try {
            // 1. username,password 를 받아서
            om = new ObjectMapper();
            RequestLogin requestLogin=om.readValue(request.getInputStream(), RequestLogin.class);

            // 2. 바탕으로 토큰을 만들어서
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(requestLogin.getEmail(), requestLogin.getPassword());

            //3. loadByUsername 을 실행
            Authentication authentication
                    = authenticationManager.authenticate(authenticationToken);

            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

            UserEntity loginMember = principalDetails.getMember();

            RedisMember findRedisMember = memberRepository
                    .findByUsername(loginMember.getEmail())
                    .orElse(null);

            if(findRedisMember==null) {
                //4. redis 에 저장
                memberRepository.save(new RedisMember(loginMember.getRole(),loginMember.getEmail()));
            }

            return authentication;


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request
            , HttpServletResponse response
            , FilterChain chain
            , Authentication authResult) throws IOException
    {
        log.info("일반로그인 user 에게 JWT 토큰 발행");
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        Tokens tokens = tokenUtils.createTokens(authResult);


        //header 에 추가
        response.addHeader(AUTH_HEADER,BEARER+tokens.getJwtToken());
        response.addHeader(REFRESH_HEADER,BEARER+tokens.getRefreshToken());
    }
}
