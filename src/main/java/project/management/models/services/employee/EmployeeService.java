package project.management.models.services.employee;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.management.models.dao.employee.IEmployeeDao;
import project.management.models.entities.Employee;

@Service
public class EmployeeService implements UserDetailsService, IEmployeeService {

	private Logger logger = LoggerFactory.getLogger(Employee.class);

	@Autowired
	private IEmployeeDao employeeDAO;

	public Employee findByUsername(String username) {
		// TODO Auto-generated method stub
		return employeeDAO.findByUsername(username);
	}

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Employee employee = employeeDAO.findByUsername(username);

		if (employee == null) {
			logger.error("Error en el login: no existe el usuario '" + username + "' en el sistema!");
			throw new UsernameNotFoundException(
					"Error en el login: no existe el usuario '" + username + "' en el sistema!");
		}

		List<GrantedAuthority> authorities = employee.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getname()))
				.peek(authority -> logger.info("Role: " + authority.getAuthority())).collect(Collectors.toList());

		return new User(employee.getUsername(), employee.getPassword(), employee.getEnabled(), true, true, true,
				authorities);
	}

	@Override
	@Transactional
	public Employee save(Employee employee) {
		return employeeDAO.save(employee);
	}

	@Override
	public void delete(Long id) {
		employeeDAO.deleteById(id);
	}

}
