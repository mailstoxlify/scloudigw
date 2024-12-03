package com.ocbc.internal.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p
						.path("/get")
						.filters(f -> f.addRequestHeader("Hello", "World"))
						.uri("http://httpbin.org:80"))
				.route(p -> p
						.path("/pokemon/**")
						.filters(f -> f.rewritePath("/pokemon/(?<segment>.*)", "/api/v2/pokemon/${segment}"))
						.uri("https://pokeapi.co"))
				.route(p -> p
						.path("/v1/pokemon/**")
						.filters(f -> f.rewritePath("/v1/pokemon/(?<segment>.*)", "/api/v2/pokemon/${segment}"))
						.uri("https://pokeapi.co"))

				.build();
	}

}
