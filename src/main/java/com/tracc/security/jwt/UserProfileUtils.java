package com.tracc.security.jwt;

import com.tracc.security.services.user.UserDetailsImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class UserProfileUtils {

    public Collection<? extends GrantedAuthority> getUserAuthorities() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }

    public UserDetailsImpl getUserDetails() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails;
    }

}
