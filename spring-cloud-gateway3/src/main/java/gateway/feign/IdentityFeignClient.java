//package gateway.feign;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@FeignClient(name = "identity-service", url = "http://localhost:8082")
//public interface IdentityFeignClient {
//
//	@GetMapping("/api/identity/auth/authroize")
//	public ResponseEntity<String> authorize();
//}
