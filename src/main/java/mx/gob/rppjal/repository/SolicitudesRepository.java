package mx.gob.rppjal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.gob.rppjal.models.entity.Solicitud;

public interface SolicitudesRepository extends JpaRepository<Solicitud, Integer> {

}
