package project.management.models.dao.role;

import org.springframework.data.repository.CrudRepository;

import project.management.models.entities.Role;

public interface IRoleDao extends CrudRepository<Role, Long> {
    
}
