package com.jbk.OnlineExam620;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.jbk.Controller")
@EntityScan("com.tka.entity")
public class OnlineExam620Application {

	public static void main(String[] args) {
		SpringApplication.run(OnlineExam620Application.class, args);
	}

}
