package com.example.userservice.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.userservice.common.exception.TokenNotFoundException;
import com.example.userservice.common.auth.PrincipalDetails;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Date;

@Slf4j
@Component
public class TokenUtils {
    /**
     * 토큰과 관련된 로직을 모아 Utils 생성
     **/
    public static final String AUTH_HEADER = "Authorization";
    public static final String REFRESH_HEADER = "refresh-token";
    public static final String BEARER = "Bearer ";
    private static  String PRIVATE_KEY;
    @Value("${Tokens.EXP_TIME_JWT}")
    private String EXP_TIME_JWT;
    @Value("${Tokens.EXP_TIME_REFRESH}")
    private String EXP_TIME_REFRESH;

    @Value("${Tokens.PRIVATE_KEY}")
    public void setPrivateKey(String privateKey) {
        PRIVATE_KEY = privateKey;
    }

    public static enum TokenType {
        access,
        refresh
    }

    public String generate(Authentication authentication,TokenType tokenType){
        log.info("generate!!");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String token = JWT.create()
                .withSubject(principalDetails.getUsername())
                .withExpiresAt(getLifeTime(tokenType))
                .withClaim("id", principalDetails.getMember().getId())
                .withClaim("username", principalDetails.getMember().getEmail())
                .sign(Algorithm.HMAC512(PRIVATE_KEY));
        log.info("token= {} ",token);
        return token;
    }

    public Tokens createTokens(Authentication authentication){

        String jwtToken = generate(authentication, TokenType.access);
        String refreshToken = generate(authentication, TokenType.refresh);

        return new Tokens(jwtToken, refreshToken);
    }

    private Date getLifeTime(TokenType type) {
        switch(type){
            case refresh:
                return new Date(System.currentTimeMillis() + (Integer.valueOf(EXP_TIME_REFRESH)));
            case access:
                return new Date(System.currentTimeMillis() + Integer.valueOf(EXP_TIME_JWT)); // 60000 * 10 : 10분
        }
        return null;
    }






}
