package com.bezkoder.spring.jwt.mongodb.exceptions.userprofile;

public class ProfileNotFoundException extends RuntimeException {

    public ProfileNotFoundException(String id) {
        super(id);
    }
}
