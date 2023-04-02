package com.example.apigatewayservice.common.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.apigatewayservice.domain.repository.MemberRedisRepository;
import com.example.apigatewayservice.domain.entity.RedisMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    private final Environment env;
    private final MemberRedisRepository redisRepository;
    private static  String PRIVATE_KEY;

    public AuthorizationHeaderFilter(Environment env, MemberRedisRepository redisRepository) {
        super(Config.class);
        this.env = env;
        this.redisRepository = redisRepository;
    }


    @Value("${Tokens.PRIVATE_KEY}")
    public void setPrivateKey(String privateKey) {
        PRIVATE_KEY = privateKey;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (!request.getURI().getPath().contains("docker build --platform amd64 -t kms199711/discovery-service_linux:1.1 .\n/actuator/")) {

                String jwtHeader=request.getHeaders().get(AUTHORIZATION).get(0);

                if(jwtHeader==null||!jwtHeader.startsWith("Bearer")){
                    return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
                }

                String jwToken=request.getHeaders().get(AUTHORIZATION).get(0).replace("Bearer ","");

                // Jwt Valid Check
                String username= null;
                try {
                    username = JWT.require(Algorithm.HMAC512(PRIVATE_KEY)).build()
                            .verify(jwToken).getClaim("username").asString();
                } catch (Exception e) {
                    e.printStackTrace();
                    return onError(exchange, "토큰에 문제가 있습니다.", HttpStatus.UNAUTHORIZED);
                }

                // Login Check
                if(username!=null){
                    RedisMember redisMember = redisRepository.findByUsername(username).orElse(null);
                    if (redisMember == null) return onError(exchange, "Not Login", HttpStatus.UNAUTHORIZED);
                }
            }

            // 통과가 된다면 무조건 로그인은 됫다는 의미이다.
            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        log.error(err);
        return response.setComplete();
    }

    static class Config {

    }
}
