package com.fiyoteam.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name = "user")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable{
	
	private static final long serialVersionUID = 3076859089557584379L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id", unique=true, nullable=false)
	private int id;
	
	@Column(name = "firstname")
	private String firstName;
	
	@Column(name = "lastname")
	private String lastName;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "role")
	private String role;
	
	@OneToMany(mappedBy = "user")
	private List<UserLanguage> userLanguage = new ArrayList<>();
	
	@OneToMany(mappedBy = "user")
	private List<UserSkill> userSkill = new ArrayList<>();
	
	public User(){
		super();
	}

	public User(int id, String firstName, String lastName, String country, String city, String email, String password,
			String role, List<UserLanguage> userLanguage, List<UserSkill> userSkill) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.country = country;
		this.city = city;
		this.email = email;
		this.password = password;
		this.role = role;
		this.userLanguage = userLanguage;
		this.userSkill = userSkill;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonIgnore 
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@JsonIgnore 
	public List<UserLanguage> getUserLanguage() {
		return userLanguage;
	}

	public void setUserLanguage(List<UserLanguage> userLanguage) {
		this.userLanguage = userLanguage;
	}
	
	@JsonIgnore 
	public List<UserSkill> getUserSkill() {
		return userSkill;
	}

	public void setUserSkill(List<UserSkill> userSkill) {
		this.userSkill = userSkill;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", country=" + country
				+ ", city=" + city + ", email=" + email + ", password=" + password + ", role=" + role
				+ ", userLanguage=" + userLanguage + ", userSkill=" + userSkill + "]";
	}

}

