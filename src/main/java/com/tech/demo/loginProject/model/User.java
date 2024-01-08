package com.tech.demo.loginProject.model;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity(name = "user")
public class User extends BaseModel{
    String userName;
    String password;
    @Enumerated(EnumType.ORDINAL)
    UserStatus userStatus;
    @Enumerated(EnumType.ORDINAL)
    LoggingStatus loggingStatus;
    Date lastTriedDate;
    Integer numTries;
    Session session;
}
