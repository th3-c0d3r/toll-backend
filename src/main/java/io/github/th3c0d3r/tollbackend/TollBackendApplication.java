package io.github.th3c0d3r.tollbackend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableAsync
@OpenAPIDefinition(info = @Info(title = "Toll Plaza India API", version = "V0", description = "GitHub: https://github.com/th3-c0d3r/toll-backend"))
public class TollBackendApplication {

	@Bean
	public WebClient.Builder getWebClientBuilder(){
		return WebClient.builder();
	}

	public static void main(String[] args) {
		SpringApplication.run(TollBackendApplication.class, args);
	}

}
