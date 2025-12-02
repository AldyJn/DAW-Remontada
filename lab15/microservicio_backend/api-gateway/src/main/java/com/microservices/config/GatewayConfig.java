package com.microservices.config;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;

@Configuration
public class GatewayConfig {

    @Bean
    public RouterFunction<ServerResponse> categoriaServiceRoute() {
        return route("categoria-service")
                .route(RequestPredicates.path("/api/categorias/**"), 
                       request -> {
                           String targetUri = "http://localhost:8081" + request.uri().getPath();
                           if (request.uri().getQuery() != null) {
                               targetUri += "?" + request.uri().getQuery();
                           }
                           return HandlerFunctions.http().handle(
                               org.springframework.web.servlet.function.ServerRequest.from(request)
                                   .uri(java.net.URI.create(targetUri))
                                   .build()
                           );
                       })
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> productoServiceRoute() {
        return route("producto-service")
                .route(RequestPredicates.path("/api/productos/**"), 
                       request -> {
                           String targetUri = "http://localhost:8082" + request.uri().getPath();
                           if (request.uri().getQuery() != null) {
                               targetUri += "?" + request.uri().getQuery();
                           }
                           return HandlerFunctions.http().handle(
                               org.springframework.web.servlet.function.ServerRequest.from(request)
                                   .uri(java.net.URI.create(targetUri))
                                   .build()
                           );
                       })
                .build();
    }
}