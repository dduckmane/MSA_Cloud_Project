package com.example.userservice.common;

import com.example.userservice.domain.entity.UserEntity;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitData {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.createCategory();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        private final CustomBCryPasswordEncoder encoder;

        public void createCategory() {
            UserEntity user1 = UserEntity.builder()
                    .email("kms199719@naver.com")
                    .name("김민성")
                    .encryptedPwd(encoder.encode("password"))
                    .userId("kms199711")
                    .build();
            UserEntity user2 = UserEntity.builder()
                    .email("kms199711@naver.com")
                    .name("김민수")
                    .encryptedPwd(encoder.encode("password"))
                    .userId("msk1234")
                    .build();
            UserEntity user3 = UserEntity.builder()
                    .email("kms199712@naver.com")
                    .name("김선주")
                    .encryptedPwd(encoder.encode("password"))
                    .userId("ksj1234")
                    .build();

            em.persist(user1);
            em.persist(user2);
            em.persist(user3);
        }
    }
}
