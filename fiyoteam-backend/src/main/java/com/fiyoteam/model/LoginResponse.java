package com.fiyoteam.model;

public class LoginResponse {
	
	int id;
	String role;
	
	public LoginResponse(){
		
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

	@Override
	public String toString() {
		return "LoginResponse [id=" + id + ", role=" + role + "]";
	}

}
