package com.example.userservice.domain.service;


import com.example.userservice.domain.dto.UserDto;
import com.example.userservice.domain.entity.UserEntity;

public interface UserService{
    UserDto createUser(UserDto userDto);

    UserDto getUserByUserId(String userId);
    Iterable<UserEntity> getUserByAll();
}
