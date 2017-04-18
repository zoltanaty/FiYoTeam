package com.fiyoteam.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "auth_token")
public class AuthToken implements Serializable {

	private static final long serialVersionUID = -1813367139991448129L;

	@Column(name = "token", unique = true, nullable = false)
	private String token;

	@Id
	@Column(name = "user_id", unique = true, nullable = false)
	private int user;

	public AuthToken() {
	}

	public AuthToken(String token, int user) {
		super();
		this.token = token;
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "AuthToken [token=" + token + ", user=" + user + "]";
	}

}
