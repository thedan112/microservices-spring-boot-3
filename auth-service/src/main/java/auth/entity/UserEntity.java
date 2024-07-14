package auth.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "login_date")
	private Date loginDate;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "status")
	private int status;

	@Column(name = "active")
	private int active;

	@Column(name = "locked")
	private int locked;

	@Column(name = "password_expired")
	private Date passwordExpired;

	@Column(name = "email")
	private String email;

	@Column(name = "type")
	private String type;
}
