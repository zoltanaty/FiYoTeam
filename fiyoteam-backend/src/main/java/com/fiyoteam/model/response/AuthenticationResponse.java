package com.fiyoteam.model.response;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable{
	
	private static final long serialVersionUID = 2256410848795209928L;
	
	private int id;
	private String email;
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

	@Override
	public String toString() {
		return "LoginResponse [id=" + id + ", email=" + email + ", role=" + role + "]";
	}

}
