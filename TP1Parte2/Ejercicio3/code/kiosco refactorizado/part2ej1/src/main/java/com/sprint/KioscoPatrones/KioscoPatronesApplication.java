package com.sprint.KioscoPatrones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KioscoPatronesApplication {

	public static void main(String[] args) {
		SpringApplication.run(KioscoPatronesApplication.class, args);
	}

}
