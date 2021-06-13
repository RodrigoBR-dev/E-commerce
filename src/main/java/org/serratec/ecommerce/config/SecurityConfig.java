package org.serratec.ecommerce.config;

import org.serratec.ecommerce.security.AuthService;
import org.serratec.ecommerce.security.JWTAuthenticationFilter;
import org.serratec.ecommerce.security.JWTAuthorizationFilter;
import org.serratec.ecommerce.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String[] CLIENTE_WHITELIST = {"/cliente"};
	private static final String[] CLIENTE_SENHA_WHITELIST = {"/cliente/recupera/**"};
	private static final String[] PRODUTO_WHITELIST = {"/produto/**"};
	private static final String[] CATEGORIA_WHITELIST = {"/categoria/**"};
	private static final String[] PEDIDO_WHITELIST = {"/pedido"};
	private static final String[] PEDIDO_PUT_WHITELIST = {"/pedido/finalizado/**"};
	private static final String[] UTIL_WHITELIST = {"/swagger-ui/**", "/v3/api-docs/**"};

	@Autowired
	AuthService service;
	
	@Autowired
	JWTUtil jwtUtil;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers(CLIENTE_SENHA_WHITELIST).permitAll()
			.antMatchers(PRODUTO_WHITELIST).permitAll()
			.antMatchers(CATEGORIA_WHITELIST).permitAll()
			.antMatchers(HttpMethod.POST, CLIENTE_WHITELIST).permitAll()
			.antMatchers(HttpMethod.DELETE, PEDIDO_WHITELIST).permitAll()
			.antMatchers(HttpMethod.PUT, PEDIDO_PUT_WHITELIST).permitAll()
			.antMatchers(HttpMethod.GET, PEDIDO_WHITELIST).permitAll()
			.antMatchers(UTIL_WHITELIST).permitAll()
		.anyRequest().authenticated();		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(new JWTAuthenticationFilter(authenticationManager(), jwtUtil),
				UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service).passwordEncoder(bCryptPasswordEncoder());
	}
}