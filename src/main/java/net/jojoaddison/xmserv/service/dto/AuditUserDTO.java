package net.jojoaddison.xmserv.service.dto;

import org.springframework.data.mongodb.core.mapping.Field;

public class AuditUserDTO {
	
	@Field("first_name")
	private String firstName;
	
	@Field("last_name")
	private String lastName;
	
	private String email, login;
	
	public AuditUserDTO(){
		
	}
	
	public AuditUserDTO lastName(String lastName){
		this.lastName = lastName;
		return this;
	}


	public AuditUserDTO firstName(String firstName){
		this.firstName = firstName;
		return this;
	}

	public AuditUserDTO email(String email){
		this.email = email;
		return this;
	}

	public AuditUserDTO login(String login){
		this.login = login;
		return this;
	}
	
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
}
