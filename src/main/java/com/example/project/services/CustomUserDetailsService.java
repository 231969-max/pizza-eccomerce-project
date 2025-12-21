package com.example.project.services;

import com.example.project.models.User;
import com.example.project.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Login attempt for: " + email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    System.out.println("User not found: " + email);
                    return new UsernameNotFoundException("User not found with email: " + email);
                });

        System.out.println("User found: " + user.getEmail() + ", Role: " + user.getRole());
        return new CustomUserDetails(user);
    }
}
