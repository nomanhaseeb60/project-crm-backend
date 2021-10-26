package project.management.models.services;

import project.management.models.entities.Employee;


public interface IEmployeeService {
	public Employee findByUsername(String username);
}
