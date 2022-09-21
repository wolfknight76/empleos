package mx.gob.rppjal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.gob.rppjal.models.entity.Categoria;

public interface CategoriasRepository extends JpaRepository<Categoria, Integer> {

}
