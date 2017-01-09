package com.meygam

import com.meygam.domain.Item
import com.meygam.service.ItemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@RestController
class DockerDemoApplication {

	@Autowired
	ItemService itemService

	@RequestMapping("/")
	String home() {
		return "Hello Docker World!"
	}

	@RequestMapping(method = RequestMethod.GET, value = "/items", produces = "application/json")
	List<Item> getItems() {
		itemService.getItems()
	}

	@RequestMapping(method = RequestMethod.POST, value = "/items", consumes = "application/json", produces = "application/json")
	String save(@RequestBody Item item) {
		itemService.save(item)
	}

	static void main(String[] args) {
		SpringApplication.run DockerDemoApplication, args
	}
}
