package com.meet.msell.service.impl;

import com.meet.msell.config.JwtProvider;
import com.meet.msell.domain.USER_ROLE;
import com.meet.msell.model.cart.Cart;
import com.meet.msell.model.user.User;
import com.meet.msell.model.user.VerificationCode;
import com.meet.msell.repository.CartRepository;
import com.meet.msell.repository.UserRepository;
import com.meet.msell.repository.VerificationCodeRepository;
import com.meet.msell.response.SignupRequest;
import com.meet.msell.service.AuthService;
import com.meet.msell.service.EmailService;
import com.meet.msell.utils.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final CartRepository cartRepository;
    private final JwtProvider jwtProvider;
    private final VerificationCodeRepository verificationCodeRepository;
    private final EmailService emailService;

    @Override
    public String createUser(SignupRequest req) throws SecurityException {

        VerificationCode verificationCode =
                verificationCodeRepository.findByEmail(req.getEmail());

        if (verificationCode == null || !verificationCode.getOtp().equals(req.getOtp()))
            throw new SecurityException("Wrong OTP. . .");

        User user = userRepository.findByEmail(req.getEmail());
        if (user == null) {
            User createdUser = new User();
            createdUser.setFullName(req.getFullName());
            createdUser.setEmail(req.getEmail());
            createdUser.setRole(USER_ROLE.ROLE_CUSTOMER);
            createdUser.setMobile("9510980488");
            createdUser.setPassword(encoder.encode(req.getOtp()));

            user = userRepository.save(createdUser);

            Cart cart = new Cart();

            cart.setUser(user);

            cartRepository.save(cart);

        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(USER_ROLE.ROLE_CUSTOMER.toString()));
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                req.getEmail(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtProvider.generateToken(authentication);
    }

    @Override
    public void sendLoginOtp(String email) throws Exception {
        String SIGNIG_PREFIX = "signin_";

        if (email.startsWith(SIGNIG_PREFIX)) {
            email = email.substring(SIGNIG_PREFIX.length());
            User user = userRepository.findByEmail(email);

            if (user != null)
                throw new Exception("user does not exist with provided email");
        }

        VerificationCode isExists = verificationCodeRepository.findByEmail(email);

        if (isExists != null)
            verificationCodeRepository.delete(isExists);

        String otp = OtpUtil.generateOtp();

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setEmail(email);
        verificationCode.setOtp(otp);
        verificationCodeRepository.save(verificationCode);

        String subject = "M-sell login/signup OTP";
        String text = "Your Login/Signup OTP is this - " + otp;
        emailService.sendVerificationOtpEmail(email, otp, subject, text);
    }
}
