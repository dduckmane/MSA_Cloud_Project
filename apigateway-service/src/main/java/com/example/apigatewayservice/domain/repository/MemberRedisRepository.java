package com.example.apigatewayservice.domain.repository;


import com.example.apigatewayservice.domain.entity.RedisMember;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MemberRedisRepository extends CrudRepository<RedisMember,String> {

    Optional<RedisMember> findByUsername(String username);

}
