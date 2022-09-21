package mx.gob.rppjal.models.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mx.gob.rppjal.models.entity.Solicitud;
import mx.gob.rppjal.models.service.ISolicitudesService;
import mx.gob.rppjal.repository.SolicitudesRepository;

@Service
public class SolicitudesServiceJpa implements ISolicitudesService {
	
	@Autowired 
	private SolicitudesRepository solicitudesRepo; 

	@Override
	public void guardar(Solicitud solicitud) {
		solicitudesRepo.save(solicitud);
	}

	@Override
	public List<Solicitud> listar() {
		return solicitudesRepo.findAll();
	}

	@Override
	public Solicitud buscarPorId(Integer idSolicitud) {
		Optional<Solicitud> optional = solicitudesRepo.findById(idSolicitud);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public void eliminar(Integer idSolicitud) {
		solicitudesRepo.deleteById(idSolicitud);
	}

	@Override
	public Page<Solicitud> listar(Pageable page) {
		return solicitudesRepo.findAll(page);
	}

}
