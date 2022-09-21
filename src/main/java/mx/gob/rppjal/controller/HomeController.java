package mx.gob.rppjal.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.gob.rppjal.models.entity.Perfil;
import mx.gob.rppjal.models.entity.Usuario;
import mx.gob.rppjal.models.entity.Vacante;
import mx.gob.rppjal.models.service.ICategoriasService;
import mx.gob.rppjal.models.service.IUsuariosService;
import mx.gob.rppjal.models.service.IVacantesService;

@Controller
public class HomeController {

	@Autowired
	private ICategoriasService serviceCategorias;

	@Autowired
	private IVacantesService serviceVacantes;
	
	@Autowired
	private IUsuariosService serviceUsuarios;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/")
	public String mostrarHome(Model model) {
		model.addAttribute("titulo", "EmpleosApp | Aplicación para publicar Ofertas de Trabajo.");
		return "home";
	}
	
	@GetMapping("/index")
	public String mostrarIndex(Authentication auth, HttpSession session) {
		String username = auth.getName();
		System.out.println("Nombre del usuario: " + username);
		
		for (GrantedAuthority rol : auth.getAuthorities()) {
			System.out.println("ROL: " + rol.getAuthority());
		}
		
		if(session.getAttribute("usuario") == null) {
			Usuario usuario = serviceUsuarios.buscarPorUsername(username);
			usuario.setPassword(null);
			System.out.println("Usuario: " + usuario);
			session.setAttribute("usuario", usuario);			
		}
		return "redirect:/";
	}
	
	@GetMapping("/signup")
	public String registrarse(Usuario usuario, Model model) {
		model.addAttribute("titulo", "EmpleosApp | Formulario de Registro");
		return "usuarios/formRegistro";
	}
	
	@PostMapping("/signup")
	public String guardarRegistro(Usuario usuario, RedirectAttributes attributes) {
		
		String pwdPlano = usuario.getPassword();
		String pwdEncriptado = passwordEncoder.encode(pwdPlano);
		usuario.setPassword(pwdEncriptado);
		
		usuario.setEstatus(1); // Activado por defecto
		usuario.setFechaRegistro(new Date()); // Fecha de Registro, la fecha actual del servidor
		
		// Creamos el Perfil que le asignaremos al usuario nuevo
		Perfil perfil = new Perfil();
		perfil.setId(3); // Perfil USUARIO
		usuario.agregar(perfil);
		
		/**
		 * Guardamos el usuario en la base de datos. El Perfil se guarda automaticamente
		 */
		serviceUsuarios.guardar(usuario);
		attributes.addFlashAttribute("msg", "El registro fue guardado correctamente!");
		return "redirect:/index";
	}
	
	@GetMapping("/search")
	public String buscar(@ModelAttribute("search") Vacante vacante, Model model) {
		System.out.println("Buscar por: " + vacante);
		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("descripcion", ExampleMatcher.GenericPropertyMatchers.contains()).withIgnoreCase();
		Example<Vacante> example = Example.of(vacante, matcher);
		List<Vacante> lista = serviceVacantes.buscarByExample(example);
		model.addAttribute("vacantes", lista);
		return "home";
	}

	@GetMapping("/about")
	public String mostrarAcerca(Model model) {
		model.addAttribute("titulo", "EmpleosApp | Acerca de Empleos App");
		return "acerca";
	}
	
	@GetMapping("/login" )
	public String mostrarLogin(Model model) {
		model.addAttribute("titulo", "EmpleosApp | Ingresar al Sistema");
		return "formLogin";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request){
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		logoutHandler.logout(request, null, null);
		return "redirect:/";
	}
	
	@GetMapping("/bcrypt/{texto}")
	@ResponseBody
	public String encriptar(@PathVariable("texto") String texto) {
		String textoEncriptado = passwordEncoder.encode(texto);
		System.out.println(" Encriptado de " + texto + " en Bcrypt: \t" + textoEncriptado);
		return texto.concat(" Encriptado en Bcrypt: \t" ).concat(textoEncriptado);
	}
	
	/**
	 * InitBinder para Strings si los detecta vacios en Data Binding los settea a Null
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@ModelAttribute
	public void setGenericos(Model model) {
		Vacante vacanteSearch = new Vacante();
		vacanteSearch.reset();
		model.addAttribute("vacantes", serviceVacantes.buscarDestacadas());
		model.addAttribute("categorias", serviceCategorias.listar());
		model.addAttribute("search", vacanteSearch);
	}

}
