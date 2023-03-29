package com.example.userservice.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tokens {

    private String jwtToken;
    private String refreshToken; // refresh

}
