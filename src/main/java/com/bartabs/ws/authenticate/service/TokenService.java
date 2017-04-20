/*
 * Copyright (c) 2017, Ron Gerschel, Jon Goldberg and Victor Lora. All rights reserved.
 * Ron Gerschel, Jon Goldberg and Victor Lora PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.bartabs.ws.authenticate.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bartabs.ws.authenticate.configuration.model.TokenParams;
import com.bartabs.ws.exceptions.TokenDecodeException;
import com.bartabs.ws.exceptions.TokenEncodeException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * The {@code TokenService} class provides the methods necessary to encode and
 * decode JWT tokens
 * 
 * @author Victor Lora
 * @version 1.0
 * @see com.bartabs.ws.authenticate.configuration.model.TokenParams
 * @since 2017-04-12
 *
 */
@Service("Authenticate.TokenService")
public class TokenService
{

	@Autowired
	private TokenParams tokenParams;

	/**
	 * Encodes a string into a token using the JSON Web Token library
	 * 
	 * @param subject
	 *            a {@code String} containing the information to be converted
	 *            into a token
	 * @return a token
	 * @throws TokenEncodeException
	 *             thrown when token encoding fails
	 */
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

	/**
	 * Decodes a token and returns the {@code Jws<Claims>} values
	 * 
	 * @param token
	 *            a JWT token
	 * @return {@code Jws<Claims> values}
	 * @throws TokenDecodeException
	 *             thrown when the token provided is invalid or a fault occurs
	 *             during decoding
	 */
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
