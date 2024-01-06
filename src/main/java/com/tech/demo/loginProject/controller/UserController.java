package com.tech.demo.loginProject.controller;

import com.tech.demo.loginProject.dtos.UserReqDto;
import com.tech.demo.loginProject.dtos.UserResDto;
import com.tech.demo.loginProject.model.LoggingStatus;
import com.tech.demo.loginProject.service.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable String userId){

        return new ResponseEntity<>(Map.of("userId", 1), HttpStatus.OK);
    }
    @PostMapping("/")
    public ResponseEntity<?> createUser(@RequestBody UserReqDto userReqDto){
        Optional<UserResDto> userResDtoOptional  = userService.createUser(userReqDto);
        if(userResDtoOptional.isPresent()){
            return new ResponseEntity<>(userResDtoOptional.get(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Could not create user due to some error.", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping("/loginUser")
    public ResponseEntity<?> loginUser(@RequestBody UserReqDto userReqDto){
        Optional<LoggingStatus> loggingStatus = userService.loginUser(userReqDto);
        if(loggingStatus.isPresent()){
            if(loggingStatus.get().equals(LoggingStatus.LOGGED_IN))
            return new ResponseEntity<>("Logged in user", HttpStatus.OK);
            if(loggingStatus.get().equals(LoggingStatus.LOGGED_OUT))
            return new ResponseEntity<>("Invalid password", HttpStatus.OK);
            else
            return new ResponseEntity<>("Account locked: try after 15 mins", HttpStatus.OK);

        }
        else{
            return new ResponseEntity<>("Could not login user due to some error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
