package com.gateaway.gateaway.services;

import com.gateaway.gateaway.clients.AuthServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TokenValidationService {
    private final AuthServiceClient authServiceClient;

    @Autowired
    public TokenValidationService(AuthServiceClient authServiceClient) {
        this.authServiceClient = authServiceClient;
    }

    public boolean validateToken(String token) {
        System.out.println(authServiceClient.validateToken(token));
        return  authServiceClient.validateToken(token);
    }
}
