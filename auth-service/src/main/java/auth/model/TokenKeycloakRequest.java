package auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenKeycloakRequest {

	private String username;
	private String password;
	private String grant_type;
	private String client_id;
	private String client_secret;
}
