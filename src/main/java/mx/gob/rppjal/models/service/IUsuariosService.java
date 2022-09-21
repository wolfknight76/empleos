package mx.gob.rppjal.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.gob.rppjal.models.entity.Usuario;

public interface IUsuariosService {

	void guardar(Usuario usuario);
	
	void eliminar(Integer idUsuario);
	
	List<Usuario> listar();

	Page<Usuario> listar(Pageable page);
	
	List<Usuario> buscarRegistrados();
	
	Usuario buscarPorId(Integer idUsuario);
	
	Usuario buscarPorUsername(String username);
	
	int bloquear(int idUsuario);
	
	int activar(int idUsuario);
}
