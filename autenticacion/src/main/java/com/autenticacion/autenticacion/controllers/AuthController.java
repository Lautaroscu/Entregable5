package com.autenticacion.autenticacion.controllers;

import com.autenticacion.autenticacion.DTO.LoginInputDTO;
import com.autenticacion.autenticacion.DTO.RegisterInputDTO;
import com.autenticacion.autenticacion.services.AuthService;
import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.annotation.Reference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@Tag(name = "AuthController", description = "Endpoints for user authentication and token validation")

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(
            summary = "Register a new user",
            description = "Registers a new user with the provided details"
                    ,
            responses = {
                    @ApiResponse(responseCode = "201", description = "User registered successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    public ResponseEntity<?> register(@RequestBody
                                      RegisterInputDTO registerInputDTO) {
        try {
            authService.register(registerInputDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    @Operation(
            summary = "Authenticates  a new user",
            description = "Authenticates a user and returns a JWT token if valid credentials are provided"
            ,
            responses = {
                    @ApiResponse(responseCode = "201", description = "User Authenticated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid credentials")
            }
    )
    public ResponseEntity<?> login(@RequestBody

                                   LoginInputDTO loginInput) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(authService.authenticate(loginInput));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/validate")
    @Operation(
            summary = "Validate a JWT",
            description = "Validates the provided JWT token and returns a boolean indicating its validity"
            ,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Token valid"),
                    @ApiResponse(responseCode = "401", description = "Token expiated or manipulated" )
            }
    )
    public ResponseEntity<Boolean> validate(@RequestBody
                                            String token) {
        try {
            return ResponseEntity.ok(authService.tokenValid(token));
        }catch (JwtException jwtException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
    }
}
