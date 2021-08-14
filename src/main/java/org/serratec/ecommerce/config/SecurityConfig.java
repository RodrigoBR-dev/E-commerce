package org.serratec.ecommerce.config;

import java.util.Arrays;

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
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String[] CLIENTE_WHITELIST = {"/cliente"};
	private static final String[] CLIENTE_SENHA_WHITELIST = {"/cliente/recupera/**"};
	private static final String[] PRODUTO_WHITELIST = {"/produto/**"};
	private static final String[] CATEGORIA_WHITELIST = {"/categoria/**"};
	private static final String[] UTIL_WHITELIST = {"/swagger-ui/**", "/v3/api-docs/**"};

	@Autowired
	AuthService service;
	
	@Autowired
	JWTUtil jwtUtil;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		var configuration = new CorsConfiguration().applyPermitDefaultValues();
		configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
		configuration.setAllowedHeaders(Arrays.asList("X-Requested-With","Origin","Content-Type","Accept","Authorization"));
	    configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Headers", "Authorization, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, "));
		http.cors().configurationSource(request -> configuration);
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers(CLIENTE_SENHA_WHITELIST).permitAll()
			.antMatchers(PRODUTO_WHITELIST).permitAll()
			.antMatchers(CATEGORIA_WHITELIST).permitAll()
			.antMatchers(HttpMethod.POST, CLIENTE_WHITELIST).permitAll()
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