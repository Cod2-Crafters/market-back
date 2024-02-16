package com.codecrafter.typhoon.config;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.codecrafter.typhoon.config.filter.EmailPasswordAuthFilter;
import com.codecrafter.typhoon.config.filter.JWTAuthFilter;
import com.codecrafter.typhoon.config.handler.BasicLoginFailHandler;
import com.codecrafter.typhoon.config.handler.BasicLoginSuccessHandler;
import com.codecrafter.typhoon.domain.entity.Member;
import com.codecrafter.typhoon.repository.member.MemberRepository;
import com.codecrafter.typhoon.service.JWTService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity(debug = true)
@Slf4j
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	private final ObjectMapper objectMapper;

	private final MemberRepository memberRepository;

	private final JWTService jwtService;

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web -> web.ignoring().requestMatchers(antMatcher("/h2-console/**")));
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
			.cors(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(
				authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
					.requestMatchers(antMatcher("/logintest")).hasRole("USER")
					.anyRequest().permitAll())
			.sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.formLogin(AbstractHttpConfigurer::disable)
			.addFilterBefore(usernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public JWTAuthFilter jwtAuthFilter() {
		return new JWTAuthFilter(jwtService);
	}

	@Bean
	public EmailPasswordAuthFilter usernamePasswordAuthenticationFilter() {
		EmailPasswordAuthFilter filter = new EmailPasswordAuthFilter(objectMapper);
		filter.setAuthenticationManager(authenticationManager());
		filter.setAuthenticationSuccessHandler(new BasicLoginSuccessHandler(jwtService, objectMapper));
		filter.setAuthenticationFailureHandler(new BasicLoginFailHandler(objectMapper));
		return filter;
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService(memberRepository));
		provider.setPasswordEncoder(passwordEncoder());
		return new ProviderManager(provider);
	}

	@Bean
	public UserDetailsService userDetailsService(MemberRepository memberRepository) {
		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				Member member = memberRepository.findByEmail(username)
					.orElseThrow(() -> new UsernameNotFoundException(username + "으로 가입된 Member가 없음!!!"));
				return new UserPrincipal(member);
			}
		};
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		// return new BCryptPasswordEncoder();
		return new PasswordEncoder() {
			@Override
			public String encode(CharSequence rawPassword) {
				System.out.println("rawPassword = " + rawPassword);
				return rawPassword.toString();
			}

			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return rawPassword.toString().equals(encodedPassword);
			}
		};
	}

}
