package com.example.apigatewayservice.domain.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@RedisHash(value = "member", timeToLive = 43222)//12시간
public class RedisMember {
    @Id
    private String id;
    private String roles; //ROLE_USER
    @Indexed
    private String username;

    public RedisMember(String roles, String username) {
        this.roles = roles;
        this.username=username;
    }

    public List<String> getRoleList(){
        if(this.roles.length()>0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }
}
