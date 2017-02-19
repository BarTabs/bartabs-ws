package com.bartabs.ws.authenticate.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.bartabs.ws.exceptions.TokenDecodeException;
import com.bartabs.ws.exceptions.TokenEncodeException;

@Service("Authenticate.TokenService")
public class TokenService
{

	public String encodeToken(final String subject) throws TokenEncodeException
	{
		try {
			final Date now = new Date();
			// final String token =
			// Jwts.builder().setSubject(subject).setIssuedAt(now)
			// .setExpiration(tokenParams.calculateExpiration(now)).setIssuer(tokenParams.getIssuer())
			// .setAudience(tokenParams.getAudience())
			// .signWith(SignatureAlgorithm.HS512,
			// tokenParams.getSecretKey()).compact();
			return null;
		} catch (final Exception ex) {
			throw new TokenEncodeException("Failed to encode token.", ex);
		}
	}

	public void decodeToken(String token) throws TokenDecodeException
	{
		try {
			// final Jws<Claims> claims =
			// Jwts.parser().setSigningKey(tokenParams.getSecretKey()).parseClaimsJws(token);
		} catch (final Exception ex) {
			throw new TokenDecodeException("Failed to decode token.", ex);
		}
	}
}
