package com.bricks_ai_lms.bricks.ai.lms.services.auth.jwt;

import com.bricks_ai_lms.bricks.ai.lms.entities.UserEnt.User;
import com.bricks_ai_lms.bricks.ai.lms.repositories.Users.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) throw new UsernameNotFoundException("User not found", null);
        return new org.springframework.security.core.userdetails.User(optionalUser.get().getUsername(), optionalUser.get().getPassword(), new ArrayList<>());
    }
}
