package com.example.userservice.common.auth;

import com.example.userservice.common.exception.MemberNotFoundException;
import com.example.userservice.domain.entity.UserEntity;
import com.example.userservice.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PrincipalDetailsService implements UserDetailsService {
    private final UserRepository memberRepository;
    /**
     * 1. UsernamePasswordAuthenticationFilter 에서
     * 2. loadUserByUsername 을 실행
     * 3. username 바탕으로 검증
     * 4. member 의 로그인 시간을 update
     * 5. UserDetails 로 감싼다.
     **/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername 실행");

        UserEntity member = memberRepository.findByEmail(username)
                .orElseThrow(()->new MemberNotFoundException("member not found"));


        return new PrincipalDetails(member);
    }
}