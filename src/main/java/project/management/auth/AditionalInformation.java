package project.management.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import project.management.models.entities.Employee;
import project.management.models.services.employee.IEmployeeService;

@Component
public class AditionalInformation implements TokenEnhancer {

	@Autowired
	private IEmployeeService employeeService;

	
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		Employee employee = employeeService.findByUsername(authentication.getName());
		Map<String, Object> info = new HashMap();

		info.put("name", employee.getName());
		info.put("surname", employee.getSurname());
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		
		return accessToken;
	}
	
}
