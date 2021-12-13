package com.example.apigatewayserver.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }
}
