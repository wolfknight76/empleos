package mx.gob.rppjal.models.service.db;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mx.gob.rppjal.models.entity.Usuario;
import mx.gob.rppjal.models.service.IUsuariosService;
import mx.gob.rppjal.repository.UsuariosRepository;

@Service
public class UsuariosServiceJpa implements IUsuariosService {
	
	@Autowired
	private UsuariosRepository usuariosRepo;

	@Override
	public void guardar(Usuario usuario) {
		usuariosRepo.save(usuario);
	}

	@Override
	public void eliminar(Integer idUsuario) {
		usuariosRepo.deleteById(idUsuario);
	}

	@Override
	public List<Usuario> listar() {
		return usuariosRepo.findAll();
	}

	@Override
	public Page<Usuario> listar(Pageable page) {
		return usuariosRepo.findAll(page);
	}
	
	@Override
	public Usuario buscarPorId(Integer idUsuario) {
		Optional<Usuario> optional = usuariosRepo.findById(idUsuario);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}	

	@Override
	public Usuario buscarPorUsername(String username) {
		return usuariosRepo.findByUsername(username);
	}

	@Override
	public List<Usuario> buscarRegistrados() {
		return usuariosRepo.findByFechaRegistroNotNull();
	}

	@Transactional
	@Override
	public int bloquear(int idUsuario) {
		return usuariosRepo.lock(idUsuario);
	}

	@Transactional
	@Override
	public int activar(int idUsuario) {
		return usuariosRepo.unlock(idUsuario);
	}
	
	

}
