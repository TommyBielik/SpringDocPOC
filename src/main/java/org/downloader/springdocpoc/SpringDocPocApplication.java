package org.downloader.springdocpoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"api", "service"})
public class SpringDocPocApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDocPocApplication.class, args);
	}
}
