package com.meet.msell.service;


import com.meet.msell.response.AuthResponse;
import com.meet.msell.response.LoginRequest;
import com.meet.msell.response.SignupRequest;

public interface AuthService {

    String createUser(SignupRequest request);

    void sendLoginOtp(String email) throws Exception;

    AuthResponse signin(LoginRequest request);
}
