package com.tech.demo.loginProject.service;

import com.tech.demo.loginProject.dtos.UserReqDto;
import com.tech.demo.loginProject.dtos.UserResDto;
import com.tech.demo.loginProject.model.LoggingStatus;

import java.util.Optional;

public interface UserService {
    UserResDto getUser(Long userId);
    Optional<UserResDto> createUser(UserReqDto userReqDto);

    Optional<LoggingStatus> loginUser(UserReqDto userReqDto);
}
