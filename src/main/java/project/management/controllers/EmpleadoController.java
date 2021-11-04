package project.management.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.management.models.entities.Employee;
import project.management.models.entities.Role;
import project.management.models.services.IEmployeeService;
import project.management.models.services.roles.IRoleService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")

public class EmpleadoController {
	@Autowired
	private IEmployeeService empleadoService;

	@Autowired
	private IRoleService roleService;

	@PostMapping("/employee/register")
	public ResponseEntity<?> registerEmployee(@Valid @RequestBody Employee employee, BindingResult result) {
		Employee newEmployee = null;
		Map<String, Object> response = new HashMap<>();

		Role role = roleService.findAll().stream().filter(r -> r.getname().equals("ROLE_USER")).findFirst()
				.orElse(null);
		employee.setRoles(Collections.singletonList(role));
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "The field'" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		try {

			newEmployee = empleadoService.save(employee);
		} catch (DataAccessException e) {
			response.put("message", "Error while inserting in database");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("message", "The employee has been registered successfully");
		response.put("employee", employee);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

}
