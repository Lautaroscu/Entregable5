package com.autenticacion.autenticacion.controllers;

import com.autenticacion.autenticacion.DTO.LoginInputDTO;
import com.autenticacion.autenticacion.DTO.RegisterInputDTO;
import com.autenticacion.autenticacion.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterInputDTO registerInputDTO) {
        try {
            authService.register(registerInputDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginInputDTO loginInput) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(authService.authenticate(loginInput));
        }catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    @PostMapping("/validate")
    public ResponseEntity<?> validate(@RequestBody String token) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.tokenValid(token));
    }

}
