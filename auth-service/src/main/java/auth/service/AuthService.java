package auth.service;

import java.util.Calendar;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import auth.dto.ResponseData;
import auth.dto.UserDetailDto;
import auth.enums.Messages;
import auth.filter.JwtTokenUtil;
import auth.model.SignInRequest;
import auth.model.SignInResponse;

@Service
public class AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	public ResponseData<?> signIn(SignInRequest req) {
		try {
			String jId = UUID.randomUUID().toString();
			Authentication authenticatetion = authenicate(req.getUsername(), req.getPassword());
			UserDetailDto userPrincal = (UserDetailDto) authenticatetion.getPrincipal();
			/* reset so lan login fail */
//			userRepo.updateLoginSuccess(String.valueOf(false), 0, new Date(), userPrincal.getId());

			final String token = jwtTokenUtil.generateToken(userPrincal, jId);
			Calendar cal = Calendar.getInstance();
			cal.setTime(jwtTokenUtil.getExpirationDateFromToken(token));

//			if (userPrincal.isChangePassword()) {
//				return new ResponseData<SignInResponse>()
//						.expired(new SignInResponse(token, cal.getTimeInMillis(), jId, req.getUsername(), null));
//			}

			return new ResponseData<SignInResponse>()
					.success(new SignInResponse(token, cal.getTimeInMillis(), jId, req.getUsername(), null));

		} catch (AuthenticationException exception) {
			String errorMessage = exception.getMessage();
			if (exception.getMessage().equalsIgnoreCase("user not found with username: " + req.getUsername())) {
				errorMessage = Messages.USER_BAD_CREDENTIAL.getDescription();
//				authenticationService.processFailureAuthen(req.getUsername());
			} else if (exception.getMessage().equalsIgnoreCase("Bad credentials")) {
				errorMessage = Messages.USER_BAD_CREDENTIAL.getDescription();
//				authenticationService.processFailureAuthen(req.getUsername());
			} else if (exception.getMessage().equalsIgnoreCase("User is disabled")) {
				errorMessage = Messages.USER_INVALID_OR_STATUS_INACTIVE.getDescription();
			} else if (exception.getMessage().equalsIgnoreCase("User account has expired")) {
				errorMessage = Messages.PASSWORD_EXPIRED.getDescription();
			} else if (exception.getMessage().equalsIgnoreCase("User account is locked")) {
				errorMessage = Messages.USER_LOCKED.getDescription();
			}
			System.err.println(exception.getMessage() + ": [" + errorMessage + "]");
			return new ResponseData<>().error(errorMessage);
		} catch (Exception e) {
			return new ResponseData<>().error(Messages.INTERNAL_SERVER_ERROR);
		}
	}

	/* validate login */
	private Authentication authenicate(String userName, String passWord) {
		return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, passWord));
	}
}
