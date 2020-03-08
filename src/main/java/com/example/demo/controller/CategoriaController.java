package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.modelo.Categoria;
import com.example.demo.modelo.Producto;
import com.example.demo.servicio.CategoriaService;
import com.example.demo.servicio.ProductoService;
import com.example.demo.servicio.UsuarioService;

@Controller
@RequestMapping("/admin")
public class CategoriaController {

	@Autowired
	ProductoService productoServicio;

	@Autowired
	UsuarioService usuarioServicio;

	@Autowired
	private CategoriaService categoriaServicio;

	@GetMapping("/listaCategorias")
	public String index(Model model) {
		model.addAttribute("categorias", categoriaServicio.findAll());
		return "admin/listaCategorias";
	}

	@GetMapping("/nuevaCategoria")
	public String nuevaCategoria(Model model) {
		model.addAttribute("categoria", new Categoria());
		return "admin/formularioCategoria";
	}

	@PostMapping("/nuevaCategoria/submit")
	public String submitNuevaCategoria(@Valid Categoria categoria, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "admin/formularioCategoria";
		} else {
			categoriaServicio.save(categoria);
			return "redirect:/admin/listaCategorias";

		}

	}

	@GetMapping("/editarCategoria/{id}")
	public String editarCategoria(@PathVariable("id") Long id, Model model) {

		Categoria categoria = categoriaServicio.findById(id);

		if (categoria != null) {
			model.addAttribute("categoria", categoria);
			return "admin/formularioCategoria";
		} else {
			return "redirect:/admin/listaCategorias";
		}

	}

	@GetMapping("/borrarCategoria/{id}")
	public String borrarCategoria(@PathVariable("id") Long id, Model model) {

		Categoria categoria = categoriaServicio.findById(id);

		Categoria otros = categoriaServicio.findById((long) 10);
		if (categoria.getId() != otros.getId() && categoria != null) {
			for (Categoria categoriaC : categoriaServicio.findAll()) {
				if (categoriaC.getId() == categoria.getId()) {
					List<Producto> productosCategoria = categoriaC.getListaProductos();
					for (Producto p : productosCategoria) {
						p.setCategoria(otros);
					}
				}
			}
			categoriaServicio.edit(otros);
			categoriaServicio.delete(categoria);
		}
		return "redirect:/admin/listaCategorias/";

	}

}
