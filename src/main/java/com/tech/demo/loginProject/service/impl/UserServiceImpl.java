package com.tech.demo.loginProject.service.impl;

import com.tech.demo.loginProject.dtos.UserReqDto;
import com.tech.demo.loginProject.dtos.UserResDto;
import com.tech.demo.loginProject.model.LoggingStatus;
import com.tech.demo.loginProject.model.User;
import com.tech.demo.loginProject.model.UserStatus;
import com.tech.demo.loginProject.repository.UserRepository;
import com.tech.demo.loginProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserResDto getUser(Long userId) {
        return null;
    }

    @Override
    public Optional<UserResDto> createUser(UserReqDto userReqDto) {
        User user = new User();
        Optional<User> userOptional = userRepository.findByUserName(userReqDto.getUserName());
        if(userOptional.isPresent()){
            return null;
        }
        user.setUserName(userReqDto.getUserName());
        user.setPassword(userReqDto.getPassword());
        user.setCreatedDttm(new Date());
        user.setUserStatus(UserStatus.ACTIVE);
        user.setIsDeleted(false);
        User savedUser = userRepository.save(user);
        UserResDto userResDto = new UserResDto();
        userResDto.setUserName(savedUser.getUserName());
        userResDto.setId(user.getId());
        return Optional.of(userResDto);
    }

    @Override
    public Optional<LoggingStatus> loginUser(UserReqDto userReqDto) {

        Optional<User> userOptional = userRepository.findByUserName(userReqDto.getUserName());
        if(userOptional.isEmpty()){
            return null;
        }
        User user = userOptional.get();
        if(user.getNumTries()==3){

        }
        Optional<LoggingStatus> response = null;
        if(user.getPassword().equals(userReqDto.getPassword())){
            user.setLoggingStatus(LoggingStatus.LOGGED_IN);
            user.setNumTries(0);
            response=  Optional.of(LoggingStatus.LOGGED_IN);
        }
        else{
            user.setNumTries(user.getNumTries()+1);
            if(user.getNumTries()==3){
                user.setLoggingStatus(LoggingStatus.LOCKED);
                response =  Optional.of(LoggingStatus.LOCKED);
                user.setLastTriedDate(new Date());
            }
            else{
                user.setLoggingStatus(LoggingStatus.LOGGED_OUT);
                response =  Optional.of(LoggingStatus.LOGGED_OUT);
            }
        }
        User savedUser = userRepository.save(user);
        return response;
    }
}
