package com.logsense.server;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
public class LogsenseServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogsenseServerApplication.class, args);
	}

}
