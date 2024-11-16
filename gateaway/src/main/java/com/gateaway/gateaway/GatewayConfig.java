package com.gateaway.gateaway;

import com.gateaway.gateaway.services.TokenValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayConfig {

    private final TokenValidationService tokenValidationService;

    @Autowired
    public GatewayConfig( @Lazy TokenValidationService tokenValidationService) {
        this.tokenValidationService = tokenValidationService;
    }

    @Bean
    public GlobalFilter customFilter() {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();

            // Permitir solicitudes a /api/auth/**
            if (path.startsWith("/api/auth")) {
                return chain.filter(exchange); // Continuar sin validar token
            }

            // Extraer el token del encabezado Authorization
            String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (token == null || !token.startsWith("Bearer ")) {
                // Si no hay token o es inválido, rechazar la solicitud
                return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token is missing or invalid"));
            }

            String tokenWithoutBearer = token.substring(7); // Remover "Bearer "

            try {
                // Validar el token usando el servicio de validación
                boolean isValid = tokenValidationService.validateToken(tokenWithoutBearer);

                if (isValid) {
                    return chain.filter(exchange); // Continuar si el token es válido
                } else {
                    return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Token"));
                }
            } catch (Exception e) {
                // Manejar errores de validación
                return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication service not available"));
            }
        };
    }
}
