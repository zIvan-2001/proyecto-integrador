package conocimiento.cultural.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import conocimiento.cultural.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{

}
