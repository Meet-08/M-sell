package com.meet.msell.response;

import com.meet.msell.domain.USER_ROLE;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AuthResponse {

    private String jwtToken;

    private String message;

    private USER_ROLE role;

}
