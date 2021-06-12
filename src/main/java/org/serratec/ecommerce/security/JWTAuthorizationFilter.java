package org.serratec.ecommerce.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class JWTAuthorizationFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		var auth = JWTUtil.getAuthentication((HttpServletRequest) request);
		SecurityContextHolder.getContext().setAuthentication(auth);
		chain.doFilter(request, response);
	}
}