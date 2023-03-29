package com.example.userservice.common;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomBCryPasswordEncoder extends BCryptPasswordEncoder {
    //시큐리티를 사용해서 member에서 password로 값을 넣을 때 BCryptPasswordEncoder 인코딩을 안하면 안됨!
}
