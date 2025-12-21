package com.example.project.services;

import com.example.project.models.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {

    private final String fullName;
    private final Long id;

    public CustomUserDetails(User user) {
        super(user.getEmail(), user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole())));
        this.fullName = user.getName();
        this.id = user.getId();
    }

    public String getFullName() {
        return fullName;
    }

    public Long getId() {
        return id;
    }
}
