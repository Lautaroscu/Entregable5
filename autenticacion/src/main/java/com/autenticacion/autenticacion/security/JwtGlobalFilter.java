//package com.autenticacion.autenticacion.security;
//
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//@Component
//@Order(1) // Prioridad del filtro, menor número = mayor prioridad
//public class JwtGlobalFilter implements GlobalFilter {
//
//    private final JwtUtil jwtUtil;
//
//    public JwtGlobalFilter(JwtUtil jwtUtil) {
//        this.jwtUtil = jwtUtil;
//    }
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        String token = extractJwtFromRequest(exchange);
//
//        // Validar el token JWT
//        if (token == null || !jwtUtil.validateToken(token)) {
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//        }
//
//        // Pasar la solicitud al siguiente filtro si el token es válido
//        return chain.filter(exchange);
//    }
//
//    private String extractJwtFromRequest(ServerWebExchange exchange) {
//        String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            return authorizationHeader.substring(7); // Remover "Bearer "
//        }
//        return null;
//    }
//}
