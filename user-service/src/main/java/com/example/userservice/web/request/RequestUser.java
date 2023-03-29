package com.example.userservice.web.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestUser {
    @NotNull(message = "Email cannot be null")
    @Size(min = 2, message = "이메일 2글자 이상이어야 합니다.")
    @Email
    private String email;
    @NotNull(message = "name cannot be null")
    @Size(min = 2, message = "이메일 2글자 이상이어야 합니다.")
    private String name;
    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "비밀번호는 최소 8글자 이상이어야 합니다.")
    private String pwd;

}
