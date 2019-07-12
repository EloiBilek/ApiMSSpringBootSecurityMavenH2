package com.apimsspringbootsecuritymavenh2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ServletComponentScan
@SpringBootApplication(scanBasePackages = "com.apimsspringbootsecuritymavenh2")
@EnableJpaRepositories("com.apimsspringbootsecuritymavenh2.model.repository")
@EntityScan("com.apimsspringbootsecuritymavenh2.model.entity")
public class ApiMsSpringBootSecurityMavenH2Application {

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(ApiMsSpringBootSecurityMavenH2Application.class, args);
	}

}
