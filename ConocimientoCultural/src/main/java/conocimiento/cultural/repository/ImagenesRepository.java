package conocimiento.cultural.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import conocimiento.cultural.entity.Imagenes;
@Repository
public interface ImagenesRepository extends CrudRepository<Imagenes, Long> {

}