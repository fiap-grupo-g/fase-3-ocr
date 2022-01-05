package br.com.fiap.g.fase3ocr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Jsr330ScopeMetadataResolver;

@SpringBootApplication
@ComponentScan(scopeResolver = Jsr330ScopeMetadataResolver.class)
public class Fase3OcrApplication {

	public static void main(String[] args) {
		SpringApplication.run(Fase3OcrApplication.class, args);
	}

}
