package com.autenticacion.autenticacion.controllers;

import com.autenticacion.autenticacion.DTO.LoginInputDTO;
import com.autenticacion.autenticacion.DTO.RegisterInputDTO;
import com.autenticacion.autenticacion.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @Operation(summary = "Register a new user", description = "Registers a new user with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<?> register(@RequestBody
                                      @Schema(description = "Details required for registering a user", required = true)
                                      RegisterInputDTO registerInputDTO) {
        try {
            authService.register(registerInputDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate a user", description = "Authenticates a user and returns a JWT token if valid credentials are provided")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "401", description = "Authentication failed",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<?> login(@RequestBody
                                   @Schema(description = "User credentials required for authentication", required = true)
                                   LoginInputDTO loginInput) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(authService.authenticate(loginInput));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/validate")
    @Operation(summary = "Validate a JWT token", description = "Validates the provided JWT token and returns a boolean indicating its validity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token is valid",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "400", description = "Invalid token or format",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Boolean> validate(@RequestBody
                                            @Schema(description = "JWT token to be validated", required = true)
                                            String token) {
        return ResponseEntity.ok(authService.tokenValid(token));
    }
}
