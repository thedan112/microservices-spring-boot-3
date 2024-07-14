package auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import auth.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String>{

	public Optional<UserEntity> findOneByUsername(String username);
}
