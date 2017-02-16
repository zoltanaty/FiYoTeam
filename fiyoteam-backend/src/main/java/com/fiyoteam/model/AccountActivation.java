package com.fiyoteam.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name = "account_activation")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountActivation implements Serializable{

	private static final long serialVersionUID = 3025274348301293940L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id", unique=true, nullable=false)
	private int id;
	
	@Column(name = "id_user")
	private int idUser;
	
	@Column(name = "activation_code")
	private String activationCode;
	
	@Column(name = "activated")
	private boolean activated;
	
	public AccountActivation(){
		//do nothing
	}

	public AccountActivation(int id, int idUser, String activationCode, boolean activated) {
		super();
		this.id = id;
		this.idUser = idUser;
		this.activationCode = activationCode;
		this.activated = activated;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	@Override
	public String toString() {
		return "AccountActivation [id=" + id + ", idUser=" + idUser + ", activationCode=" + activationCode
				+ ", activated=" + activated + "]";
	}
	
}
