package com.tech.demo.loginProject.service.impl;

import com.tech.demo.loginProject.model.Session;
import com.tech.demo.loginProject.model.SessionStatus;
import com.tech.demo.loginProject.model.User;
import com.tech.demo.loginProject.service.SessionService;
import com.tech.demo.loginProject.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    TokenService jwtTokenService;
    @Override
    public Session loginSession(User user) {
        Session session = new Session();
        session.setUser(user);
        session.setToken(jwtTokenService.createToken(user,new Date()));
        return null;
    }

    @Override
    public Session logoutSession(User user) {
        user.getSession().setSessionStatus(SessionStatus.INACTIVE);
        return user.getSession();
    }
}
