package com.example.url;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class UrLdApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrLdApplication.class, args);
	}

}
