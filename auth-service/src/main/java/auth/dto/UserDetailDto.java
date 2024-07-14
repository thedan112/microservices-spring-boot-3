package auth.dto;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import auth.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize
public class UserDetailDto implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String id;
	private String username;
	private String password;
	private String email;
	private Boolean active;
	private String type;
	private List<String> accessApis;
	private List<String> accessViews;
	private Collection<? extends GrantedAuthority> authorities;
	private Date expiredPassword;
	private Boolean isLocked;
	// date expired
	private Date expiredSession;

	public UserDetailDto(UserEntity user, List<String> accessApis, List<String> accessViews) {

		List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getType()));

		this.id = user.getId();
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.active = user.getActive() == 1 ? true : false;
		this.type = user.getType();
		this.accessApis = accessApis;
		this.accessViews = accessViews;
		this.authorities = authorities;
		this.expiredPassword = user.getPasswordExpired();
		this.isLocked = user.getLocked() == 1 ? true : false;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.isLocked ? false : true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return active;
	}

	public boolean hadPermissionApi(String uri) {
		return accessApis.stream().anyMatch(s -> s.toLowerCase(Locale.ROOT).equals(uri.toLowerCase(Locale.ROOT)));
	}

	public boolean hadPermissionView(String uri) {
		return accessViews.stream().anyMatch(s -> s.toLowerCase(Locale.ROOT).equals(uri.toLowerCase(Locale.ROOT)));
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getAccessApis() {
		return accessApis;
	}

	public void setAccessApis(List<String> accessApis) {
		this.accessApis = accessApis;
	}

	public List<String> getAccessViews() {
		return accessViews;
	}

	public void setAccessViews(List<String> accessViews) {
		this.accessViews = accessViews;
	}

	public Boolean getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}

	public long getExpiredSession() {
		return expiredSession.getTime();
	}

	public void setExpiredSession(Date expiredSession) {
		this.expiredSession = expiredSession;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
}
