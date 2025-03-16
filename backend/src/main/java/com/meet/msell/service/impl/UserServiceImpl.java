package com.meet.msell.service.impl;

import com.meet.msell.config.JwtProvider;
import com.meet.msell.model.user.User;
import com.meet.msell.repository.UserRepository;
import com.meet.msell.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String token) {
        String email = jwtProvider.getEmailFromToken(token);

        return findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email " + email);
        }
        return user;
    }
}
