package com.tracc.exceptions.userprofile;

public class ProfileNotFoundException extends RuntimeException {

    public ProfileNotFoundException(String id) {
        super(id);
    }
}
