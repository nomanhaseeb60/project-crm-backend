package project.management.models.dao;

import org.springframework.data.repository.CrudRepository;

import project.management.models.entities.Employee;

public interface IEmployeeDao extends CrudRepository<Employee, Long> {
	public Employee findByUsername(String username);
}
