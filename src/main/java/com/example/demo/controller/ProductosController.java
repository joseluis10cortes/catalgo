package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.modelo.Producto;
import com.example.demo.servicio.CategoriaService;
import com.example.demo.servicio.ProductoService;
import com.example.demo.servicio.UsuarioService;

@Controller
@RequestMapping("/admin")
public class ProductosController {

	@Autowired
	ProductoService productoServicio;

	@Autowired
	UsuarioService usuarioServicio;
	
	@Autowired
	private CategoriaService categoriaServicio;
	
	@GetMapping("/listaProductos")
	public String index(Model model) {
		model.addAttribute("productos", productoServicio.findAll());
		return "admin/listaProductos";
	}

	@GetMapping("/nuevo")
	public String nuevaProducto(Model model) {
		model.addAttribute("producto", new Producto());
		model.addAttribute("categorias", categoriaServicio.findAll());
		return "admin/formularioProducto";
	}

	@PostMapping("/nuevo/submit")
	public String submitNuevoProducto(@Valid Producto producto, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("categorias", categoriaServicio.findAll());
			return "admin/formularioProducto";
		} else {
			productoServicio.save(producto);
			return "redirect:/admin/listaProductos";

		}

	}

	@GetMapping("/editar/{id}")
	public String editarProducto(@PathVariable("id") Long id, Model model) {

		Producto producto = productoServicio.findById(id);

		if (producto != null) {
			model.addAttribute("producto", producto);
			model.addAttribute("categorias", categoriaServicio.findAll());
			return "admin/formularioProducto";
		} else {
			return "redirect:/admin/listaProductos";
		}

	}
	
	

	@GetMapping("/borrar/{id}")
	public String borrarProducto(@PathVariable("id") Long id, Model model) {

		Producto producto = productoServicio.findById(id);

		if (producto != null) {
			productoServicio.delete(producto);
		}

		return "redirect:/admin/listaProductos/";

	}
	

}
