package auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponse {

	public SignInResponse(String token, long expiry, String jId, String username, String email) {
		this.token = token;
		this.jId = jId;
		this.username = username;
		this.email = email;
		this.expiry = expiry;
	}

	private String token;
	private String type = "Bearer ";
	private String jId;
	private String username;
	private String email;
	private long expiry;
}
