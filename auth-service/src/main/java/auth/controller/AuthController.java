package auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import auth.model.SignInRequest;
import auth.model.TokenKeycloakRequest;
import auth.service.AuthService;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

	@Autowired
	AuthService service;
	
	@PostMapping("/sign-in")
	public ResponseEntity<?> signIn(@RequestBody SignInRequest req) {
		return ResponseEntity.ok(service.signIn(req));
	}
	
	@PostMapping("/token")
	public ResponseEntity<?> signIn(@RequestBody TokenKeycloakRequest req) {
		return ResponseEntity.ok("Keycloak success");
	}
}
