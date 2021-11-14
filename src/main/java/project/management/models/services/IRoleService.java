package project.management.models.services;
import java.util.List;

import project.management.models.entities.Role;

public interface IRoleService {
	
	public Role save(Role roles);

	public void delete(Long id);

    public List<Role> findAll();
}