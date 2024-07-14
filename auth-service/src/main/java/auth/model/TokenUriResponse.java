package auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenUriResponse {

	private String jId;
	private String vendor;
	private String url;
	private String username;
	private long expiry;
}
