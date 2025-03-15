package com.meet.msell.controller;

import com.meet.msell.domain.USER_ROLE;
import com.meet.msell.model.user.VerificationCode;
import com.meet.msell.response.ApiResponse;
import com.meet.msell.response.AuthResponse;
import com.meet.msell.response.SignupRequest;
import com.meet.msell.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@Valid @RequestBody SignupRequest req) {
        String token = authService.createUser(req);
        AuthResponse response = new AuthResponse(
                token,
                "Register Successfully",
                USER_ROLE.ROLE_CUSTOMER
        );
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/sent/otp")
    public ResponseEntity<ApiResponse> sentOtpHandler(@RequestBody VerificationCode req) throws Exception {
        authService.sendLoginOtp(req.getEmail());
        ApiResponse response = new ApiResponse();
        response.setMessage("Otp sent successfully");
        return ResponseEntity.status(201).body(response);
    }

}
