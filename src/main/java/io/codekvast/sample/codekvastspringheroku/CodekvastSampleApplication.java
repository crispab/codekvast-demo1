package io.codekvast.sample.codekvastspringheroku;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class CodekvastSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodekvastSampleApplication.class, args);
	}
}
