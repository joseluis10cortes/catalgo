package com.example.demo.servicio;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.modelo.Categoria;
import com.example.demo.modelo.Producto;
import com.example.demo.repos.ProductoRepository;

@Service
public class ProductoService extends BaseService<Producto, Long, ProductoRepository>{

	public List<Producto> findAllByCategoria(Long categoria) {
		return repositorio.findByCategoriaId(categoria);
	}



}
