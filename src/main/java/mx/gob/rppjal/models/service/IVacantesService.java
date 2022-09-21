package mx.gob.rppjal.models.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.gob.rppjal.models.entity.Vacante;

public interface IVacantesService {

	List<Vacante> listar();
	
	Vacante buscarPorId(Integer id);
	
	void guardar(Vacante vacante);
	
	List<Vacante> buscarDestacadas();
	
	void eliminar(Integer id);
	
	List<Vacante> buscarByExample(Example<Vacante> example);
	
	Page<Vacante> listar(Pageable page);
}
