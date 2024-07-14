package business.service.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class HelloWorldController {

	@GetMapping("/hello")
	public ResponseEntity<?> helloWorld(HttpServletRequest request){
		return ResponseEntity.ok("Hello World");
	}
}