package com.tech.demo.loginProject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "session")
public class Session {
    @OneToOne
    User user;
    String token;
    SessionStatus sessionStatus;
}
