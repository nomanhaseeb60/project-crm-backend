package project.management.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.management.models.dao.IRoleDao;
import project.management.models.entities.Role;

@Service
public class RoleService implements IRoleService {

    @Autowired
	private IRoleDao rolesDAO;

    @Override
    public Role save(Role roles) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<Role> findAll() {
        return (List<Role>) rolesDAO.findAll();
    }
    
}
