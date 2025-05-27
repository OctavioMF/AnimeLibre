package com.DouDev.AnimeLibre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AnimeLibreApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnimeLibreApplication.class, args);
	}

}
