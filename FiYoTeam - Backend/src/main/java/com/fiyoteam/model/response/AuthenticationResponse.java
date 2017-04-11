package com.fiyoteam.model.response;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable{
	
	private static final long serialVersionUID = 2256410848795209928L;
	
	private int id;
	private String email;
	private String firstName;
	private String lastName;
	private String role;
	
	public AuthenticationResponse(){
		
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

	@Override
	public String toString() {
		return "AuthenticationResponse [id=" + id + ", email=" + email + ", firstName=" + firstName + ", lastName="
				+ lastName + ", role=" + role + "]";
	}

}
