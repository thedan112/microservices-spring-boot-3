package auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenUriRequest {

	private String uri;
	private String username;
	private long expiry;

	@Override
	public String toString() {
		return "username: " + username + "#url: " + uri + "#expiry: " + expiry;
	}
}
