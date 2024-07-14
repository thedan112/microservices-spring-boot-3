package gateway.util;

import java.util.Date;
import java.util.function.Function;

import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtil {

	private static final String jwtSecret = "ab104c80-9173-4eb2-bfd1-8c672aaaa464-52dc4263-25fb-4719-b401-3e5fced5c4c8-9c8bbcf2-7f2c-4b89-ae39-c1a18acdd977-768535b6-599f1c35-56ce-4226-94b3-0382cf90d82e-bf93-4109-9b2b-a8581a057a91-b7df8df8-9918-4455-aa1e-60082a9cd97dff";
	private static final int jwtExpirationMinute = 60;
	private static final String jwtIssuer = "test.com.vn";
	private static final String tokenSecret = "920617e9-c87e-4b27-a3e2-65ee91d21903";

//	public void validateToken(final String token) {
//		Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
//	}
//
//	private Key getSignKey() {
//		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
//		return Keys.hmacShaKeyFor(keyBytes);
//	}

	public Boolean validateToken(String token) {
//		if (userDetails == null) {
		try {
			getAllClaimsFromToken(token);
			return true;
		} catch (MalformedJwtException e) {
			System.out.println("Invalid JWT token: {} " + e.getMessage());
		} catch (ExpiredJwtException e) {
			System.out.println("JWT token is expired: {} " + e.getMessage());
		} catch (UnsupportedJwtException e) {
			System.out.println("JWT token is unsupported: {} " + e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println("JWT claims string is empty: {} " + e.getMessage());
		}
		return false;
//		} else {
//			final String username = getUsernameFromToken(token);
//			return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//		}
	}

	// retrieve username from jwt token
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	// retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	// for retrieveing any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecret)).parseClaimsJws(token)
				.getBody();
	}

	// check if the token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
}
