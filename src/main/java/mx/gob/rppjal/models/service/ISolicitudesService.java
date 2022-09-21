package mx.gob.rppjal.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.gob.rppjal.models.entity.Solicitud;

public interface ISolicitudesService {

	void guardar(Solicitud solicitud);
	
	List<Solicitud> listar();
	
	Solicitud buscarPorId(Integer idSolicitud);
	
	void eliminar(Integer idSolicitud);
	
	Page<Solicitud> listar(Pageable page);
	
}
