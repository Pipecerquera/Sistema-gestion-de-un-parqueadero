package com.AuthNode.auth.web.Config;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Component
public class JwtUtil {
	
	private static String SECRET_KEY = "iotagrot-admin-develop-statement";
	public static Algorithm ALGORItHM = Algorithm.HMAC256(SECRET_KEY);
	
	public String create(String username) {
		return JWT.create()
				.withSubject(username)
				.withIssuer("rise")
				.withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(15)))
				.sign(ALGORItHM);
	}
	
	public boolean isValid(String jwt) {
		try {
			JWT.require(ALGORItHM)
			.build()
			.verify(jwt);
			return true;
		} catch (JWTVerificationException e) {
			return false;
		}
	}
	
	public String getUsername(String jwt) {
	return JWT.require(ALGORItHM)
			.build()
			.verify(jwt)
			.getSubject();
	}
	
	
	
}
