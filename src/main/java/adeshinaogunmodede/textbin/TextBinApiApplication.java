package adeshinaogunmodede.textbin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TextBinApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TextBinApiApplication.class, args);
	}

}
