package com.bartabs.ws.configuration.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bartabs.ws.authenticate.service.TokenService;

@Component("Interceptor.TokenInterceptor")
public class TokenInterceptor extends HandlerInterceptorAdapter
{

	@Autowired
	@Qualifier("Authenticate.TokenService")
	private TokenService tokenService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
		final String token = request.getHeader("authorization");

		if (token == null || token.isEmpty()) {
			return false;
		}

		try {
			// Jws<Claims> claims = tokenService.decodeToken(token);
			// if (claims != null) {
			if (token.equals("test")) {
				return true;
			}

			return false;
		} catch (final Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
}
