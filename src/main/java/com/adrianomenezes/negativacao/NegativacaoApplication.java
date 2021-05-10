package com.adrianomenezes.negativacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class NegativacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(NegativacaoApplication.class, args);
	}


}