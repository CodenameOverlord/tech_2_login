package com.tech.demo.loginProject.service;

import com.tech.demo.loginProject.model.Session;
import com.tech.demo.loginProject.model.User;

public interface SessionService {
    Session loginSession(User user);
    Session logoutSession(User user);
}
