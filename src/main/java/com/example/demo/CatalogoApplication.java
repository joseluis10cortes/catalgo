package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.modelo.Usuario;
import com.example.demo.servicio.CategoriaService;
import com.example.demo.servicio.UsuarioService;

@SpringBootApplication
public class CatalogoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogoApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner init(UsuarioService servicio, BCryptPasswordEncoder passwordEncoder) {
		return args -> {
			
			for(Usuario u : servicio.findAll()) {
				u.setPassword(passwordEncoder.encode(u.getPassword()));
				servicio.save(u);
			}
			
		};
	}

}
