package com.prime.auth.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prime.auth.config.SecurityConfig;
import com.prime.auth.projection.AuthUserProjection;
import com.prime.auth.projection.LoginProjection;
import com.prime.auth.repository.UserRepository;

@Service("userDetailsService")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService, UserDetailsService {

	private final UserRepository repository;

	@Autowired
	public UserServiceImpl(UserRepository repository) {
		this.repository = repository;

	}

	@Override
	public Optional<AuthUserProjection> getAuthUserByLoginId(String username) {
		return repository.getAuthUserByLoginId(username);
	}

	@Async
	@Override
	@Transactional
	public void updateFailedAttemptes(String id, int failed) {
		repository.updateFailedAttemptes(id, failed);

	}

	@Async
	@Override
	@Transactional
	public void lockUser(String id) {

		List<Character> t = new ArrayList<>();
		t.add('c');

		repository.lockUser(id);
	}

	@Async
	@Override
	@Transactional
	public void updateUserLastLoginDetails(Date date, String clientIp, String id, String deviceId, String latitude,
			String longitude) {
		repository.updateUserLastLoginDetails(date, clientIp, id, deviceId, latitude, longitude);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LoginProjection user = repository.findByLoginId(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found for:" + username));
		return new User(user.getLoginId(), user.getPassword(), SecurityConfig.grantedAuthorities);
	}

	public static void main(String[] args) {

		System.out.println(getFirstRiptedLetter("apple"));

	}

	private static Character getFirstRiptedLetter(String anyData) {

		Map<Character, Integer> tempMap = new HashMap<>();

		for (int i = 0; i < anyData.length(); i++) {

			if (tempMap.containsKey(anyData.charAt(i))) {

				return anyData.charAt(i);

			} else {
				tempMap.put(anyData.charAt(i), 1);

			}

		}

		return null;
	}

}
