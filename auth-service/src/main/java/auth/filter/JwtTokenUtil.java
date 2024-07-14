package auth.filter;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import auth.dto.UserDetailDto;
import auth.model.TokenUriRequest;
import auth.model.TokenUriResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -1621986004303263166L;

	private static final String jwtSecret = "ab104c80-9173-4eb2-bfd1-8c672aaaa464-52dc4263-25fb-4719-b401-3e5fced5c4c8-9c8bbcf2-7f2c-4b89-ae39-c1a18acdd977-768535b6-599f1c35-56ce-4226-94b3-0382cf90d82e-bf93-4109-9b2b-a8581a057a91-b7df8df8-9918-4455-aa1e-60082a9cd97dff";
	private static final int jwtExpirationMinute = 60;
	private static final String jwtIssuer = "test.com.vn";
	private static final String tokenSecret = "920617e9-c87e-4b27-a3e2-65ee91d21903";

	@Autowired
	ObjectMapper objectMapper;

	public UserDetailDto getSessionFromJwt(String token) throws JsonProcessingException {
		try {
			byte[] privateKeyBytes = DatatypeConverter.parseBase64Binary(jwtSecret);
			String userClaim = Jwts.parser().requireIssuer(jwtIssuer).setSigningKey(privateKeyBytes)
					.parseClaimsJws(token).getBody().get("authenticated", String.class);
			byte[] bytes = userClaim.getBytes(StandardCharsets.UTF_8);
			String json = new String(Base64.getDecoder().decode(bytes));
			/* expire session */
			UserDetailDto userDetail = objectMapper.readValue(json, UserDetailDto.class);
			return userDetail;
		} catch (Exception e) {
			System.out.println("Get Session From Jwt Ex: " + e.getMessage());
		}
		return null;
	}

	public String base64DecodeUri(TokenUriRequest req, UserDetailDto usr) {
		try {
			TokenUriResponse res = new TokenUriResponse();
			res.setExpiry(req.getExpiry());
			res.setUrl(req.getUri());
			res.setUsername(req.getUsername());
			res.setJId(usr.getId() + tokenSecret);
			String json = objectMapper.writeValueAsString(res);
			byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
			String base64 = Base64.getEncoder().encodeToString(bytes);
			return base64;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "";
	}

	public TokenUriRequest getURIFromReq(String uri) {
		try {
			byte[] bytes = uri.getBytes(StandardCharsets.UTF_8);
			String json = new String(Base64.getDecoder().decode(bytes));
			return objectMapper.readValue(json, TokenUriRequest.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
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

	// generate token for user
	public String generateToken(UserDetails userDetails, String jId) throws JsonProcessingException {
		return doGenerateToken(userDetails, jId);
	}

	private String doGenerateToken(UserDetails userDetails, String jId) throws JsonProcessingException {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		byte[] privateKeyBytes = DatatypeConverter.parseBase64Binary(jwtSecret);
		SecretKey secretKey = new SecretKeySpec(privateKeyBytes, signatureAlgorithm.getJcaName());
		Date expiry = new Date((new Date()).getTime() + jwtExpirationMinute * 60 * 1000);
		UserDetailDto userPrincipal = (UserDetailDto) userDetails;
		Calendar cal = Calendar.getInstance();
		cal.setTime(expiry);
		userPrincipal.setExpiredSession(cal.getTime());
		String json = objectMapper.writeValueAsString(userPrincipal);
		byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
		String base64 = Base64.getEncoder().encodeToString(bytes);

		return Jwts.builder().setId(jId).setSubject(userDetails.getUsername()).setIssuer(jwtIssuer)
				.setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(expiry).claim("authenticated", base64)
				.signWith(SignatureAlgorithm.HS256, secretKey).compact();
	}

	// validate token
	public Boolean validateToken(String token, UserDetails userDetails) {
		if (userDetails == null) {
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
		} else {
			final String username = getUsernameFromToken(token);
			return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
		}

	}
}
