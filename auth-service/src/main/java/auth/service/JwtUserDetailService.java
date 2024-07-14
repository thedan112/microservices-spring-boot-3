package auth.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import auth.dto.UserDetailDto;
import auth.entity.UserEntity;
import auth.enums.Messages;
import auth.repository.UserRepository;

@Service
public class JwtUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserEntity> users = userRepository.findOneByUsername(username);
		if (users.isEmpty()) {
			throw new UsernameNotFoundException(Messages.NOT_FOUND.getDescription() + " by username " + username);
		}
		UserEntity user = users.get();
		return new UserDetailDto(user, new ArrayList<>(), new ArrayList<>());
	}
}
