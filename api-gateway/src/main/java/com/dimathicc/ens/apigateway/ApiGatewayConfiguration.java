package com.dimathicc.ens.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {
    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/api/users/**")
                        .uri("lb://user-service"))
//                TODO: connect security service to Eureka
//                .route(p -> p
//                        .path("/api/auth")
//                        .uri("lb://security-service"))
                .route(p -> p
                        .path("/api/files")
                        .uri("lb://file-service"))
                .build();
    }
}
