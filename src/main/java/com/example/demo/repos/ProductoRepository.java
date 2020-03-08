package com.example.demo.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.modelo.Categoria;
import com.example.demo.modelo.Producto;

@Repository
public interface ProductoRepository  extends JpaRepository<Producto, Long>{

	List<Producto> findByCategoriaId(Long categoria);

}
