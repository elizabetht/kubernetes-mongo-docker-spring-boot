package com.meygam

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@RestController
class DockerDemoApplication {

	@RequestMapping("/")
	String home() {
		return "Hello Docker World!"
	}

	static void main(String[] args) {
		SpringApplication.run DockerDemoApplication, args
	}
}
