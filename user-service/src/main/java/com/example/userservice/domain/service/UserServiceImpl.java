package com.example.userservice.domain.service;

import com.example.userservice.common.exception.MemberNotFoundException;
import com.example.userservice.domain.dto.UserDto;
import com.example.userservice.domain.entity.UserEntity;
import com.example.userservice.domain.repository.UserRepository;
import com.example.userservice.common.CustomBCryPasswordEncoder;
import com.example.userservice.web.FeignErrorDecoder;
import com.example.userservice.web.feignclient.ResumeServiceClient;
import com.example.userservice.web.response.ResponseResume;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CustomBCryPasswordEncoder encoder;
    private final Environment env;
    private final ResumeServiceClient resumeServiceClient;
    private final CircuitBreakerFactory circuitBreakerFactory;

    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd(encoder.encode(userDto.getPwd()));

        userRepository.save(userEntity);

        return mapper.map(userEntity, UserDto.class);
    }

    public UserDto getUserByUserId(String userId) {
        UserEntity user = userRepository
                .findByUserId(userId)
                .orElseThrow(() -> new MemberNotFoundException("일치하는 회원이 없습니다."));

        UserDto userDto = new ModelMapper().map(user, UserDto.class);

//        // RestTemplate 사용
//        String resumeServiceUrl = env.getProperty("resume_service.url", userId);
//        ResponseEntity<List<ResponseResume>> result =
//                restTemplate.exchange(resumeServiceUrl
//                        , HttpMethod.GET
//                        , null
//                        , new ParameterizedTypeReference<List<ResponseResume>>() {});

        // Feign Client
//        List<ResponseResume> resumes = resumeServiceClient.getResume(userId);

        log.info("user 서비스에서 order 서비스 호출 전");
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");
        // run method 안에 있는 메서드에 circuitBreaker 가 적용이 된다.
        // 그 후에 그 코드에 문제가 생겨서 circuitBreaker 가 작동을 한다면 어떤 값을 반환할 지 알려준다.
        List<ResponseResume> resumes
                = circuitBreaker.run(() -> resumeServiceClient.getResume(userId)
                , throwable -> new ArrayList<>());
        log.info("user 서비스에서 order 서비스 호출 후");

        userDto.setResponseResumes(resumes);
        return userDto;
    }


    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }


}
