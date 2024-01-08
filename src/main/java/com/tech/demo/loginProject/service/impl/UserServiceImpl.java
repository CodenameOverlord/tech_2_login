package com.tech.demo.loginProject.service.impl;

import com.tech.demo.loginProject.dtos.UserReqDto;
import com.tech.demo.loginProject.dtos.UserResDto;
import com.tech.demo.loginProject.model.*;
import com.tech.demo.loginProject.repository.UserRepository;
import com.tech.demo.loginProject.service.SessionService;
import com.tech.demo.loginProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    SessionService sessionService;

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
        user.setNumTries(0);
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
        if(user.getUserStatus()==UserStatus.LOCKED){
            long timeDifference = new Date().getTime()-user.getLastTriedDate().getTime();
            long diffInMin = (timeDifference/ (1000 * 60))% 60;
            if(diffInMin>15){
                user.setNumTries(0);
                user.setUserStatus(UserStatus.ACTIVE);
            }
            else return Optional.of(LoggingStatus.LOGGED_OUT);

        }

        Optional<LoggingStatus> response = null;
        if(user.getPassword().equals(userReqDto.getPassword())){
            Session session = sessionService.loginSession(user);
            user.setSession(session);
            user.setLoggingStatus(LoggingStatus.LOGGED_IN);
            user.setNumTries(0);
            response=  Optional.of(LoggingStatus.LOGGED_IN);
        }
        else{
            user.setNumTries(user.getNumTries()+1);
            if(user.getNumTries()>=3){
                user.setLoggingStatus(LoggingStatus.LOCKED);
                response =  Optional.of(LoggingStatus.LOCKED);
                user.setLastTriedDate(new Date());
            }
            else{
                user.setSession(sessionService.logoutSession(user));

                user.setLoggingStatus(LoggingStatus.LOGGED_OUT);
                response =  Optional.of(LoggingStatus.LOGGED_OUT);
            }
        }
        User savedUser = userRepository.save(user);
        return response;
    }

    @Override
    public Optional<Boolean> checkDashboard(UserReqDto userReqDto) {
        Optional<User> userOptional = userRepository.findByUserName(userReqDto.getUserName());
        if(userOptional.isEmpty()){
            return null;
        }
        User user = userOptional.get();
        if(user.getSession().getSessionStatus()== SessionStatus.ACTIVE){
            return Optional.of(true);
        }
        else{
            return Optional.of(false);
        }
    }
}
