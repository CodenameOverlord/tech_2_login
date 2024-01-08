package com.tech.demo.loginProject.service;

import com.tech.demo.loginProject.model.User;

import java.util.Date;

public interface TokenService {
    String createToken(User user, Date currentDTTM);
}
