package com.example.googledemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RequestPredicates.GET;
import static org.springframework.web.servlet.function.RouterFunctions.route;
import static org.springframework.web.servlet.function.ServerResponse.ok;

@SpringBootApplication
@EnableR2dbcRepositories(basePackages = {"com.example.googledemo.repository"})
public class GoogledemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoogledemoApplication.class, args);
	}
	@Bean
	public RouterFunction<ServerResponse> htmlRouter(
			@Value("classpath:/public/index.html") Resource html) {
		return route(GET("/")
                 	, request
				-> ok().contentType(MediaType.TEXT_HTML).body(html)
		);
	}
	@Bean
	RouterFunction<ServerResponse> staticResourceRouter() {
		return RouterFunctions.resources("/**", new ClassPathResource("public/"));
	}




}
