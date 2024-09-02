package com.guilherme.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.guilherme.demo.domain.entity.Image;
import com.guilherme.demo.domain.enums.ImageExtencion;
import com.guilherme.demo.repository.ImageReporitory;

@SpringBootApplication
@EnableJpaAuditing
@CrossOrigin("*")
public class DemoApplication {
	/*
	 * 
	 @Bean
	 public CommandLineRunner commandLineRunner(@Autowired ImageReporitory repository){
		return args -> {
			Image image = Image
			.builder()
			.extencion(ImageExtencion.PNG)
			.name("myImage")
			.tags("teste")
			.size(1000L)
			.build();
			repository.save(image);
		};
	}
	*/
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	
	}
}
