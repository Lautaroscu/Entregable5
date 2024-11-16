package com.gateaway.gateaway.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth-service", url = "http://localhost:8084")
public interface AuthServiceClient {

    @PostMapping("/api/auth/validate")
    boolean validateToken(@RequestBody String token);
}

