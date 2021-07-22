package com.prime.auth.controller;

import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.prime.auth.config.SecurityConfig;
import com.prime.auth.projection.AuthUserProjection;
import com.prime.auth.security.jwt.AuthenticatedUser;
import com.prime.auth.security.jwt.JwtTokenFactory;
import com.prime.auth.security.jwt.LoginRequest;
import com.prime.auth.security.jwt.RawAccessJwtToken;
import com.prime.auth.security.jwt.RefreshToken;
import com.prime.auth.security.jwt.TokenVerifier;
import com.prime.auth.service.UserService;
import com.prime.common.enums.Status;
import com.prime.common.exception.ApplicationException;
import com.prime.common.exception.InvalidJwtToken;
import com.prime.common.library.JwtSettings;
import com.prime.common.library.JwtToken;
import com.prime.common.response.TokenResponse;
import com.prime.common.util.Global;
import com.prime.common.util.StringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("auth")
@Api(tags = "Auth", value = "Auth API", description = "Auth Api")
public class AuthController {

	private final JwtTokenFactory tokenFactory;

	private final TokenVerifier tokenVerifier;

	private final UserService userService;

	@Autowired
	@Qualifier("encoder")
	BCryptPasswordEncoder encoder;

	private static final String NO_AUTHENTICATION = "No authentication data provided";
	private static final String ACCOUNT_INACTIVE = "Account is inactive";
	private static final String ACCOUNT_LOCK = "Account is Locked. Please contact admin";
	private static final String BAD_CREDENTIALS = "Login Failed. Username or Password not valid.";

	@Autowired
	public AuthController(JwtTokenFactory tokenFactory, TokenVerifier tokenVerifier, UserService userService) {
		this.tokenFactory = tokenFactory;
		this.tokenVerifier = tokenVerifier;
		this.userService = userService;
	}

	@ApiOperation("Auth Token")
	@PostMapping("/login")
	public TokenResponse login(@RequestBody LoginRequest request, HttpServletRequest httpServletRequest)
			throws ApplicationException {

		String username = request.getUsername();
		String password = request.getPassword();

		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			throw new ApplicationException(NO_AUTHENTICATION);
		}

		AuthUserProjection loginUserDetails = userService.getAuthUserByLoginId(username)
				.orElseThrow(() -> new ApplicationException("User not found with:" + username));

		if (Boolean.TRUE.equals(loginUserDetails.getLocked())) {
			throw new ApplicationException(ACCOUNT_LOCK);
		}

		if (Status.INACTIVE == loginUserDetails.getStatus()) {
			throw new ApplicationException(ACCOUNT_INACTIVE);
		}

		if (!encoder.matches(password, loginUserDetails.getPassword())) {
			updateFailedAttemnt(loginUserDetails);
			throw new ApplicationException(BAD_CREDENTIALS);
		}

		AuthenticatedUser userContext = new AuthenticatedUser(loginUserDetails.getId(), loginUserDetails.getLoginId(),
				loginUserDetails.getTimeZone(), loginUserDetails.getLangCode(), loginUserDetails.getDecimalScale(),
				loginUserDetails.getTenantType(), loginUserDetails.getTenantId());

		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(
				new UsernamePasswordAuthenticationToken(userContext, null, SecurityConfig.grantedAuthorities));
		SecurityContextHolder.setContext(context);

		updateLoginData(request, httpServletRequest, loginUserDetails);

		return new TokenResponse(tokenFactory.createAccessJwtToken(userContext).getToken(),
				tokenFactory.createRefreshToken(userContext).getToken());

	}

	private void updateFailedAttemnt(AuthUserProjection loginUserDetails) {
		int failed = loginUserDetails.getFailedAttempts() == null ? 0 : loginUserDetails.getFailedAttempts() + 1;
		if (failed > 6) {
			userService.lockUser(loginUserDetails.getId());
		}
		userService.updateFailedAttemptes(loginUserDetails.getId(), failed);
	}

	private void updateLoginData(LoginRequest request, HttpServletRequest httpServletRequest,
			AuthUserProjection loginUserDetails) {
		userService.updateUserLastLoginDetails(new Date(), Global.getClientIp(httpServletRequest),
				loginUserDetails.getId(), request.getDeviceId(), request.getLatitude(), request.getLongitude());
	}

	@ApiOperation("Refresh token")
	@GetMapping(value = "/refresh")
	public @ResponseBody JwtToken refreshToken(@RequestHeader(JwtSettings.JWT_TOKEN_HEADER_PARAM) String tokenPayload) {

		RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);

		RefreshToken refreshToken = RefreshToken.create(rawToken, JwtSettings.TOKEN_SIGNING_KEY)
				.orElseThrow(InvalidJwtToken::new);

		String jti = refreshToken.getJti();
		if (!tokenVerifier.verify(jti)) {
			throw new InvalidJwtToken();
		}
		String subject = refreshToken.getSubject();

		AuthUserProjection loginUserDetails = userService.getAuthUserByLoginId(subject)
				.orElseThrow(() -> new UsernameNotFoundException(Global.USER_NOT_FOUND));

		AuthenticatedUser userContext = new AuthenticatedUser(loginUserDetails.getId(), loginUserDetails.getLoginId(),
				loginUserDetails.getTimeZone(), loginUserDetails.getLangCode(), loginUserDetails.getDecimalScale(),
				loginUserDetails.getTenantType(), loginUserDetails.getTenantId());

		userContext.setGrantedAuthorities(Collections.singletonList(SecurityConfig.DEFAULT_ROLE));
		return tokenFactory.createAccessJwtToken(userContext);
	}

}
