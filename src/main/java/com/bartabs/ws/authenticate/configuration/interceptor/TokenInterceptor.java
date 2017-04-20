/*
 * Copyright (c) 2017, Ron Gerschel, Jon Goldberg and Victor Lora. All rights reserved.
 * Ron Gerschel, Jon Goldberg and Victor Lora PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.bartabs.ws.authenticate.configuration.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bartabs.ws.authenticate.service.TokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

/**
 * The {@code TokenInterceptor} class intercepts requests coming into the API
 * and verifies that they have been authenticated and have permission to use our
 * API. It decodes the token using the (@code io.jsonwebtoken} library.
 * 
 * @author Victor A. Lora
 * @version 1.0
 * @see com.bartabs.ws.authenticate.service.TokenService
 * @since 2017-04-12
 *
 */
@Component("Interceptor.TokenInterceptor")
public class TokenInterceptor extends HandlerInterceptorAdapter
{

	@Autowired
	@Qualifier("Authenticate.TokenService")
	private TokenService tokenService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
		try {
			// Pull the request token from the request headers
			final String token = request.getHeader("authorization");

			if (token == null || token.isEmpty()) {
				return false;
			}

			// Use the the {@code TokenService} {@code decodeToken()} function
			// to parse the token and verify it's validity
			Jws<Claims> claims = tokenService.decodeToken(token);
			if (claims != null) {
				return true;
			}

			return false;
		} catch (final Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
}
