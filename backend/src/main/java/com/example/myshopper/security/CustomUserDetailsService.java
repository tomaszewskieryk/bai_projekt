package com.example.myshopper.security;

import com.example.myshopper.model.User;
import com.example.myshopper.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = null;
        try {
            user = userService.getUserByEmail(email);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        UserBuilder builder;

        if (user != null) {
            builder = org.springframework.security.core.userdetails.User.withUsername(email);
            builder.disabled(false);
            builder.password(user.getPassword());
            builder.authorities("ROLE_USER");
        } else {
            throw new UsernameNotFoundException("User: " + email + "not found!");
        }

        return builder.build();
    }
}