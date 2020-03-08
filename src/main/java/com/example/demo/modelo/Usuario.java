package com.example.demo.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
public class Usuario {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO )
	private Long id;
	
	private String nombre;
	private String email;
	private String password;
	private boolean admin;
	
	
	public Usuario(String nombre,String email, String password,boolean admin) {
		this.nombre = nombre;
		this.email = email;
		this.password = password;
		this.admin = admin;
	}
	

}
