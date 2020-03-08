package com.example.demo.seguridad;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.modelo.Usuario;
import com.example.demo.servicio.UsuarioService;


@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	UsuarioService usuarioServicio;

	public UserDetailsServiceImpl(UsuarioService servicio) {
		this.usuarioServicio = servicio;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioServicio.buscarPorEmail(username);
		
		UserBuilder builder = null;
		
		if (usuario != null) {
			builder = User.withUsername(username);
			builder.disabled(false);
			builder.password(usuario.getPassword());
			if (usuario.isAdmin()) {
				builder.authorities(new SimpleGrantedAuthority("ROLE_ADMIN"));
			} else {
				builder.authorities(new SimpleGrantedAuthority("ROLE_USER"));
			}
		} else {
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
		
		return builder.build();
	}

}
