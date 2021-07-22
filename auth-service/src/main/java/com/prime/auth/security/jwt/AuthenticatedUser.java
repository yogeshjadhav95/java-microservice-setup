package com.prime.auth.security.jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jsoniter.JsonIterator;
import com.jsoniter.annotation.JsonIgnore;
import com.prime.common.enums.Status;
import com.prime.common.library.LoginUserDetails;
import com.prime.common.util.EncryptionUtils;
import com.prime.common.util.Global;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public class AuthenticatedUser extends LoginUserDetails implements UserDetails {

	private static final long serialVersionUID = 5902868566092307406L;

	private List<String> grantedAuthorities = new ArrayList<>();

	AuthenticatedUser() {

	}

	public AuthenticatedUser(String id, String loginId, String timeZone, String langCode, Integer decimalScale,
			String tenantType, String tenantId) {
		super();
		this.id = id;
		this.loginId = loginId;
		this.timeZone = timeZone;
		this.langCode = langCode;
		this.decimalScale = decimalScale;
		this.tenantType = tenantType;
		this.tenantId = tenantId;
	}

	public static LoginUserDetails get(Jws<Claims> jwsClaims) {
		String json = EncryptionUtils.decrypt(jwsClaims.getBody().get(Global.KEY_ID, String.class));
		return JsonIterator.deserialize(json, LoginUserDetails.class);
	}

	@JsonIgnore
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return grantedAuthorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	@Override
	public String getUsername() {
		return this.getLoginId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return Boolean.TRUE.equals(this.getLocked());
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return Status.ACTIVE == getStatus();
	}

	public List<String> getGrantedAuthorities() {
		return grantedAuthorities;
	}

	public void setGrantedAuthorities(List<String> grantedAuthorities) {
		this.grantedAuthorities = grantedAuthorities;
	}

}
