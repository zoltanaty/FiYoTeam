package com.fiyoteam.model.DTO;

import java.io.Serializable;

public class AuthenticationDTO implements Serializable{
	
	private static final long serialVersionUID = 2256410848795209928L;
	
	private int id;
	private String email;
	private String firstName;
	private String lastName;
	private String role;
	private String token;

	
	public AuthenticationDTO(){
		
	}

	public AuthenticationDTO(int id, String email, String firstName, String lastName, String role, String token) {
		super();
		this.id = id;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.token = token;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "AuthenticationResponse [id=" + id + ", email=" + email + ", firstName=" + firstName + ", lastName="
				+ lastName + ", role=" + role + ", token=" + token + "]";
	}

}
