package identity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import identity.component.JwtAuthConverter;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticateController {

	@Autowired
	JwtAuthConverter converter;
	
	@PostMapping("/authroize")
	public ResponseEntity<?> validateToken(@RequestParam(required = false) String uri) {
//		List<String> roles = converter.convert(uri);
		return ResponseEntity.ok("Valid authorize success");
	}
}
