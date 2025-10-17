package kr.aisawan.locks_testing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LocksTestingApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocksTestingApplication.class, args);
	}

}
