package project.management.models.services.employee;

import project.management.models.entities.Employee;


public interface IEmployeeService {
	public Employee findByUsername(String username);

	public Employee save(Employee employee);

	public void delete(Long id);

}
