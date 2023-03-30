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
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CustomBCryPasswordEncoder encoder;
    private final Environment env;
    private final ResumeServiceClient resumeServiceClient;

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
        List<ResponseResume> resumes = resumeServiceClient.getResume(userId);

        userDto.setResponseResumes(resumes);
        return userDto;
    }


    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }


}
