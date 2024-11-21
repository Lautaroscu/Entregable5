package com.gateaway.gateaway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Order(1) // Define la prioridad del filtro
public class AuthGlobalFilter implements GlobalFilter {
    private WebClient.Builder webClientBuilder;

    @Autowired
    public AuthGlobalFilter(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (path.startsWith("/api/auth")) {
            return chain.filter(exchange);
        }
        // Extraer el header "Authorization"
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Si el token no está presente o no es válido, devolver 401
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7); // Remover "Bearer "

        // Llamar al servicio de autenticación para validar el token
        return webClientBuilder.build()
                .post()
                .uri("http://localhost:8084/api/auth/validate") // URL de tu servicio de autenticación
                .bodyValue(token) // Enviar el token como el body de la solicitud
                .retrieve()
                .bodyToMono(Boolean.class) // Esperar una respuesta boolean
                .flatMap(isValid -> {
                    if (!isValid) {
                        // Si el token no es válido, devolver 401
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    }
                    // Si el token es válido, continuar con el flujo
                    return chain.filter(exchange);
                })
                .onErrorResume(error -> {
                    // Si ocurre un error en la llamada al servicio de autenticación, devolver 500
                    exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                    return exchange.getResponse().setComplete();
                });
    }
}
