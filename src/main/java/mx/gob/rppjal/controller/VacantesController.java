package mx.gob.rppjal.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.gob.rppjal.models.entity.Vacante;
import mx.gob.rppjal.models.service.ICategoriasService;
import mx.gob.rppjal.models.service.IVacantesService;
import mx.gob.rppjal.util.Utileria;

@Controller
@RequestMapping("/vacantes")
public class VacantesController {
	
	@Value("${empleosapp.ruta.imagenes}")
	private String ruta;

	@Autowired
	private IVacantesService serviceVacantes;

	@Autowired
	private ICategoriasService serviceCategorias;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	@GetMapping("/create")
	public String crear(Vacante vacante, Model model) {
		model.addAttribute("titulo", "EmpleosApp | Creación de Ofertas de Trabajo");
		return "vacantes/formVacante";
	}

	/*
	 * @PostMapping("/save") public String guardar(@RequestParam("nombre") String
	 * nombre, @RequestParam("descripcion") String
	 * descripcion, @RequestParam("estatus") String estatus, @RequestParam("fecha")
	 * String fecha, @RequestParam("destacado") int
	 * destacado, @RequestParam("salario") Double salario, @RequestParam("detalles")
	 * String detalles, Model model) { System.out.println("Nombre Vacante" +
	 * nombre); System.out.println("Descripción: " + descripcion);
	 * System.out.println("Estatus: " + estatus);
	 * System.out.println("Fecha Publicación: " + fecha);
	 * System.out.println("Destacado: " + destacado);
	 * System.out.println("Salario Ofrecido: " + salario);
	 * System.out.println("Detalles: " + detalles); model.addAttribute("titulo",
	 * "Lista de Vacantes"); return "vacantes/listVacantes"; }
	 */

	@PostMapping("/save")
	public String guardar(Vacante vacante, BindingResult result, RedirectAttributes attributes,
			@RequestParam("archivoImagen") MultipartFile multiPart) {
		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				System.out.println("Ocurrio un error: " + error.getDefaultMessage());
			}
			return "vacantes/formVacante";
		}

		if (!multiPart.isEmpty()) {

			String nombreImagen = Utileria.guardarArchivo(multiPart, ruta);
			
			if (nombreImagen != null) { // La imagen si se subio
				
				// Procesamos la variable nombreImagen
				vacante.setImagen(nombreImagen);
			}
		}

		serviceVacantes.guardar(vacante);
		attributes.addFlashAttribute("msg", "Registro Guardado");
		System.out.println("Guardar vacante: " + vacante.getId());
		return "redirect:/vacantes/index";
	}

	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idVacante, RedirectAttributes attributes) {
		System.out.println("Borrar vacante con id: " + idVacante);
		serviceVacantes.eliminar(idVacante);
		attributes.addFlashAttribute("msg", "La vacante fue eliminada!");
		return "redirect:/vacantes/index";
	}

	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") int idVacante, Model model) {
		Vacante vacante = serviceVacantes.buscarPorId(idVacante);
		System.out.println("Editar vacante: " + vacante.getId());
		model.addAttribute("vacante", vacante);
		return "vacantes/formVacante";
	}
	
	@GetMapping("/view/{id}")
	public String verDetalle(@PathVariable("id") int idVacante, Model model) {
		Vacante vacante = serviceVacantes.buscarPorId(idVacante);
		System.out.println("Ver vacante: " + vacante.getId());
		model.addAttribute("vacante", vacante);
		return "vacantes/detalle";
	}
	
	@ModelAttribute
	public void setGenericos(Model model) {
		model.addAttribute("categorias", serviceCategorias.listar());
	}

	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		List<Vacante> lista = serviceVacantes.listar();
		model.addAttribute("titulo", "EmpleosApp | Listado de Ofertas de Trabajo.");
		model.addAttribute("mensajeH4", "Listado de Ofertas de Trabajo.");
		model.addAttribute("vacantes", lista);
		return "vacantes/listVacantes";
	}
	
	@GetMapping(value = "/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		Page<Vacante> lista = serviceVacantes.listar(page);
		model.addAttribute("vacantes", lista);
		model.addAttribute("titulo", "EmpleosApp | Listado de Ofertas de Trabajo.");
		model.addAttribute("mensajeH4", "Listado de Ofertas de Trabajo.");
		return "vacantes/listVacantes";
	}

}
