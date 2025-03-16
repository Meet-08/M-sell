package com.meet.msell.service;

import com.meet.msell.model.user.User;

public interface UserService {

    User findUserByJwtToken(String token);

    User findUserByEmail(String email);

}
