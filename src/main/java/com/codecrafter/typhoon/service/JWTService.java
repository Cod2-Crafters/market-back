package com.codecrafter.typhoon.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.codecrafter.typhoon.config.Userprincipal;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class JWTService {

	public final SecretKey KEY = Keys.hmacShaKeyFor(
		Decoders.BASE64.decode("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
	private static final long ACCESS_TOKEN_EXPIRATION_TIME = 30 * 60 * 1000; //30분
	private static final long REFRESH_TOKEN_EXPIRATION_TIME = 24 * 60 * 1000; //1일

	public static final String ACCESS_TOKEN_STRING = "accessToken";
	public static final String ACCESS_TOKEN_PREFIX = "Bearer ";
	public static final String ACCESS_TOKEN_HEADER = "Authorization";

	public static final String REFRESH_TOKEN_STRING = "refreshToken";



	public String createAccessToken(Userprincipal userprincipal) {



		String accessToken = Jwts.builder()
			.subject(String.valueOf(userprincipal.getId()))
			.claim("email", userprincipal.getUsername())
			.issuedAt(new Date())
			.expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
			.signWith(KEY)
			.compact();
		return accessToken;
	}

	public String createRefreshToken(Userprincipal userprincipal) {
		String refreshToken = Jwts.builder()
			.subject(String.valueOf(userprincipal.getId()))
			.claim("email", userprincipal.getUsername())
			.issuedAt(new Date())
			.expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
			.signWith(KEY)
			.compact();
		return refreshToken;
	}


	public Claims getClaims(String token){
		Claims claims = Jwts.parser().verifyWith(KEY).build().parseSignedClaims(token).getPayload();
		return claims;
	}




}
