package com.bartabs.ws.authenticate.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bartabs.ws.configuration.model.TokenParams;
import com.bartabs.ws.exceptions.TokenDecodeException;
import com.bartabs.ws.exceptions.TokenEncodeException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service("Authenticate.TokenService")
public class TokenService
{

	@Autowired
	private TokenParams tokenParams;

	public String encodeToken(final String subject) throws TokenEncodeException
	{
		try {
			final Date now = new Date();
			final String token = Jwts.builder().setSubject(subject).setIssuedAt(now)
					.setExpiration(tokenParams.calculateExpiration(now)).setIssuer(tokenParams.getIssuer())
					.setAudience(tokenParams.getAudience())
					.signWith(SignatureAlgorithm.HS512, tokenParams.getSecretKey()).compact();

			return token;
		} catch (final Exception ex) {
			throw new TokenEncodeException("Failed to encode token.", ex);
		}
	}

	public Jws<Claims> decodeToken(String token) throws TokenDecodeException
	{
		try {
			final Jws<Claims> claims = Jwts.parser().setSigningKey(tokenParams.getSecretKey()).parseClaimsJws(token);
			return claims;
		} catch (final Exception ex) {
			throw new TokenDecodeException("Failed to decode token.", ex);
		}
	}
}
