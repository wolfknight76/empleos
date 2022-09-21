package mx.gob.rppjal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.gob.rppjal.models.entity.Categoria;
import mx.gob.rppjal.models.service.ICategoriasService;

@Controller
@RequestMapping("/categorias")
public class CategoriasController {
	
	@Autowired
	private ICategoriasService serviceCategoria;
	
	@ModelAttribute
	public void setGenericos(Model model) {
		model.addAttribute("categorias", serviceCategoria.listar());
	}	

	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		List<Categoria> lista = serviceCategoria.listar();
		model.addAttribute("titulo", "EmpleosApp | Listado de Categorías de ofertas de Trabajo.");
		model.addAttribute("mensajeH4", "Listado de Categorías de ofertas de Trabajo.");
		model.addAttribute("categorias", lista);
		return "categorias/listCategorias";
	}
	
	@GetMapping(value = "/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		Page<Categoria> lista = serviceCategoria.listar(page);
		model.addAttribute("categorias", lista);
		model.addAttribute("titulo", "EmpleosApp | Listado de Categorías de ofertas de Trabajo.");
		model.addAttribute("mensajeH4", "Listado de Categorías de ofertas de Trabajo.");
		return "categorias/listCategorias";
	}

	@GetMapping("/create")
	public String crear(Categoria categoria, Model model) {
		model.addAttribute("titulo", "EmpleosApp | Creación de Categorías de ofertas de Trabajo");
		model.addAttribute("mensajeH4", "Datos de la categoría");
		return "categorias/formCategoria";
	}

	@PostMapping("/save")
	public String guardar(Categoria categoria, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				System.out.println("Ocurrio un error: " + error.getDefaultMessage());
			}

			return "categorias/formCategoria";
		}
		serviceCategoria.guardar(categoria);
		attributes.addFlashAttribute("msg", "Los datos de la categoría fueron guardados!");
		System.out.println("Categoria: " + categoria);
		return "redirect:/categorias/index";
	}

	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idCategoria, RedirectAttributes attributes) {
		System.out.println("Borrar categoría con id: " + idCategoria);
		serviceCategoria.eliminar(idCategoria);
		attributes.addFlashAttribute("msg", "La categoría fue eliminada!");
		return "redirect:/categorias/index";
	}

	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") int idCategoria, Model model) {
		Categoria categoria = serviceCategoria.buscarPorId(idCategoria);
		System.out.println("Editar categoria: " + categoria.getId());
		model.addAttribute("titulo", "EmpleosApp | Edición de Categorías de ofertas de Trabajo");
		model.addAttribute("mensajeH4", "Datos de la categoría");
		model.addAttribute("categoria", categoria);
		return "categorias/formCategoria";
	}
}
