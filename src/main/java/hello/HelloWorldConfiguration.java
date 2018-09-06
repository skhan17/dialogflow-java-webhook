package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloWorldConfiguration {

	public static void main(String[] args) {
		System.out.println("IN MAIN CLASS");

		SpringApplication.run(HelloWorldConfiguration.class, args);
	}

}
