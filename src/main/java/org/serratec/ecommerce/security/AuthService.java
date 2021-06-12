package org.serratec.ecommerce.security;

import java.util.Optional;

import org.serratec.ecommerce.entities.ClienteEntity;
import org.serratec.ecommerce.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

	@Autowired
	ClienteRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<ClienteEntity> cliente = repository.findByAtivoTrueAndUserNameOrEmailOrCpf(username, username, username);
		if (cliente.isEmpty()) {
			return null;
		}
		return new UserSS(cliente.get().getId(), cliente.get().getUserName(), cliente.get().getSenha());
	}
}