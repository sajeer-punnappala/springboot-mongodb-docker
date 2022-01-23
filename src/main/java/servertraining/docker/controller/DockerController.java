package servertraining.docker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/docker")
public class DockerController {

	@GetMapping("/hello")
	public String sayHello(){
		return "Hello Sajeer";
		
	}
}
