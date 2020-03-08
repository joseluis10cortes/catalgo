package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.modelo.Categoria;
import com.example.demo.modelo.Producto;
import com.example.demo.modelo.Usuario;
import com.example.demo.servicio.CategoriaService;
import com.example.demo.servicio.ProductoService;
import com.example.demo.servicio.UsuarioService;

@Controller
@RequestMapping("/")
public class UsuarioController {

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private ProductoService productoService;

	@Autowired
	private UsuarioService usuarioServicio;

	
	@GetMapping("nosotros")
	public String Nosotros(Model model) {
		return "nosotros";
	}

	@GetMapping("registrarse")
	public String Registrase(Model model) {

		model.addAttribute("user", new Usuario());
		return "registrarse";

	}

	@PostMapping("nuevoUsuario")
	public String NuevoUsuario(@ModelAttribute("user") Usuario u, Model model) {
		BCryptPasswordEncoder pass = new BCryptPasswordEncoder();
		u.setPassword(pass.encode(u.getPassword()));
		usuarioServicio.save(u);
		return "redirect:/login";

	}

	@GetMapping("/")
	public String Index(@RequestParam(name = "idCategoria", required = false) Long idCategoria, Model model) {

		model.addAttribute("categorias", categoriaService.findAll());

		List<Producto> productos;

		if (idCategoria == null) {
			productos = productoService.findAll();
		} else {
			productos = productoService.findAllByCategoria(idCategoria);
		}
		model.addAttribute("listaProductos", productos);

		return "index";
	}

	@GetMapping("/productos/{id}")
	public String productoDetalle(@PathVariable("id") Long id, Model model) {
		Producto p = productoService.findById(id);
		if (p != null) {
			model.addAttribute("producto", p);
			return "detalle";
		}

		return "redirect:/";

	}

}
