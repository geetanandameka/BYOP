package com.example.url;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
//import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class UrLdApplication {
	public static void main(String[] args) {
		SpringApplication.run(UrLdApplication.class, args);
	}
}
