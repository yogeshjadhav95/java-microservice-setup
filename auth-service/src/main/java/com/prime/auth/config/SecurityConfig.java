package com.prime.auth.config;

import java.util.Collections;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import com.prime.auth.filter.JwtRequestFilter;
import com.prime.auth.security.jwt.JwtAuthenticationEntryPoint;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // TODO: large size token not workin in prod we need to find better
													// solution for that
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	public static final String DEFAULT_ROLE = "USER";

	public static Set<GrantedAuthority> grantedAuthorities = Collections
			.singleton(new SimpleGrantedAuthority(DEFAULT_ROLE));

	private static final String FORM_BASED_LOGIN_ENTRY_POINT = "/auth/login";
	private static final String TOKEN_REFRESH_ENTRY_POINT = "/auth/refresh";
	private static final String[] pathToSkip = { TOKEN_REFRESH_ENTRY_POINT, FORM_BASED_LOGIN_ENTRY_POINT,
			"/swagger-ui/**", "/swagger-ui.html", "/api-docs/**", "/v2/**", "/v3/**", "/swagger-resources/**",
			"/webjars/**" };

	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configerGlobal(AuthenticationManagerBuilder managerBuilder) throws Exception {
		managerBuilder.userDetailsService(userDetailsService).passwordEncoder(encoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors()//
				.and().formLogin().disable().httpBasic().disable()//
				.csrf().disable()//
				.headers().frameOptions().deny()//
				.and().headers().frameOptions().disable()//
				// .and().headers().featurePolicy("geolocation 'self'")//
				.and().headers().httpStrictTransportSecurity().maxAgeInSeconds(31536000)//
				.and().and().headers().xssProtection().block(true)//
				.and().and().headers().referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.NO_REFERRER)//
				.and().and().headers().cacheControl().disable()//
				.and().headers()
				.contentSecurityPolicy(
						"script-src 'self' https://trustedscripts.example.com; object-src https://trustedplugins.example.com; report-uri /csp-report-endpoint/")
				.and().and().headers().addHeaderWriter(new StaticHeadersWriter("Expect-CT", "max-age=86400,enforce"))
				.addHeaderWriter(
						new StaticHeadersWriter("Strict-Transport-Security", "max-age=31536000 ; includeSubDomains"))
				.addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.DENY))
				.addHeaderWriter(new StaticHeadersWriter("Server", "Server"))
				.addHeaderWriter(new StaticHeadersWriter("X-Content-Security-Policy", "default-src 'self'"))
				.addHeaderWriter(new StaticHeadersWriter("X-WebKit-CSP", "default-src 'self'"))//
				.and().authorizeRequests().antMatchers(pathToSkip).permitAll().anyRequest().authenticated()//
				.and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)//
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//
				.and().addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
