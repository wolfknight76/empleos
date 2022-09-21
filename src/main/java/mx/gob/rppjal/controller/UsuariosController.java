package mx.gob.rppjal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.gob.rppjal.models.entity.Usuario;
import mx.gob.rppjal.models.service.IUsuariosService;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

	@Autowired
	private IUsuariosService serviceUsuarios;
	
	@GetMapping("/index")
	public String monstrarIndex(Model model) {
		List<Usuario> lista = serviceUsuarios.listar();
		model.addAttribute("usuarios", lista);
		model.addAttribute("titulo", "EmpleosApp | Listado de Usuarios.");
		model.addAttribute("mensajeH4", "Listado de Usuarios.");
		return "usuarios/listUsuarios";
	}
	
	@GetMapping("/indexPaginate")
	public String monstrarIndexPaginado(Model model, Pageable page) {
		Page<Usuario> lista = serviceUsuarios.listar(page);
		model.addAttribute("usuarios", lista);
		model.addAttribute("titulo", "EmpleosApp | Listado de Usuarios.");
		model.addAttribute("mensajeH4", "Listado de Usuarios.");
		return "usuarios/listUsuarios";
	}
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idUsuario, RedirectAttributes attributes) {
		System.out.println("Borrar usuario con id: " + idUsuario);
		serviceUsuarios.eliminar(idUsuario);
		attributes.addFlashAttribute("msg", "El usuario fue eliminado!");
		return "redirect:/usuarios/index";
	}
	
	@GetMapping("/lock/{id}")
	public String bloquear(@PathVariable("id") int idUsuario, RedirectAttributes attributes) {		
		serviceUsuarios.bloquear(idUsuario);
		attributes.addFlashAttribute("msg", "El usuario fue bloqueado y no tendra acceso al sistema.");		
		return "redirect:/usuarios/indexPaginate";
	}
	
	@GetMapping("/unlock/{id}")
	public String activar(@PathVariable("id") int idUsuario, RedirectAttributes attributes) {		
    	serviceUsuarios.activar(idUsuario);
		attributes.addFlashAttribute("msg", "El usuario fue activado y ahora tiene acceso al sistema.");		
		return "redirect:/usuarios/indexPaginate";
	}
}
