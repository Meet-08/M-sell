package com.meet.msell.controller;

import com.meet.msell.model.user.User;
import com.meet.msell.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/profile")
    public ResponseEntity<User> createUserHandler(
            @RequestHeader("Authorization") String token
    ) {
        User user = userService.findUserByJwtToken(token);
        return ResponseEntity.ok(user);
    }

}
