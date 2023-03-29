package com.example.apigatewayservice.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorizationHeaderFilterTest {
    @Test
    public void decode() {
        String s = JWT.require(Algorithm.HMAC512("safeKing")).build()
                .verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrbXMxOTk3MTlAbmF2ZXIuY29tIiwiaWQiOjEsImV4cCI6MTY4MDAxMDgxNywidXNlcm5hbWUiOiJrbXMxOTk3MTlAbmF2ZXIuY29tIn0.iyezzIKgyzWzD-1FbWw-9YjWveDtvy19-e-ZoOwY8LIxciWCX2jYvn24ntXkhDCu98jvFmJnlWaCuYvxErtQKA").getClaim("username").asString();

        System.out.println("s = " + s);
    }
}