package mx.gob.rppjal.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.gob.rppjal.models.entity.Categoria;

public interface ICategoriasService {

	void guardar(Categoria categoria);
	
	List<Categoria> listar();
	
	Categoria buscarPorId(Integer idCategoria);
	
	void eliminar(Integer idCategoria);
	
	Page<Categoria> listar(Pageable page);
	
}
