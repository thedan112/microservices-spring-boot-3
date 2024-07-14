//package gateway.filter;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//import gateway.util.JwtUtil;
//
//@Component
//public class KeycloakOauth2Filter extends AbstractGatewayFilterFactory<KeycloakOauth2Filter.Config> {
//
//	@Autowired
//	private RouteValidator validator;
//
////	@Autowired
////	IdentityFeignClient feignClient;
//
//	@Autowired
//	RestTemplate template;
//
//	@Autowired
//	private JwtUtil jwtUtil;
//
//	public KeycloakOauth2Filter() {
//		super(Config.class);
//	}
//
//	@Override
//	public GatewayFilter apply(Config config) {
//
//		return ((exchange, chain) -> {
//			if (validator.isSecured.test(exchange.getRequest())) {
//				// header contains token or not
//				if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
//					throw new RuntimeException("missing authorization header");
//				}
//				String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
//				 if (authHeader != null && authHeader.startsWith("Bearer ")) {
//					authHeader = authHeader.substring(7);
//					try {
//						HttpHeaders headers = new HttpHeaders();
//						headers.setBearerAuth(authHeader);
//						ResponseEntity<Boolean> resp = template.postForEntity("localhost:8082/api/identity/auth/authroize", new HttpEntity<>(headers), Boolean.class);
//						if (resp.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(401))) {
//							throw new RuntimeException("401 unauthorized access to application");
//						} else {
//							if (!resp.getBody()) {
//								throw new RuntimeException("403 denined access to application");
//							}
//						}
//					} catch (Exception e) {
//						System.out.println("invalid access...!");
//						throw new RuntimeException("401 unauthorized access to application");
//					}
//				} else {
//					throw new RuntimeException("401 unauthorized access to application");
//				}
//			}
//			return chain.filter(exchange);
//		});
//	}
//
//	public static class Config {
//
//	}
//}
