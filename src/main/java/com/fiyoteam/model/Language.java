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
@Table(name = "language")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Language implements Serializable{
	
	private static final long serialVersionUID = 2543856591312554251L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id", unique=true, nullable=false)
	private int id;
	
	@Column(name = "language")
	private String language;
	
	@OneToMany(mappedBy = "language")
	private List<UserLanguage> userLanguage = new ArrayList<>();
	
	public Language(){
		super();
	}

	public Language(int id, String language, List<UserLanguage> userLanguage) {
		super();
		this.id = id;
		this.language = language;
		this.userLanguage = userLanguage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@JsonIgnore 
	public List<UserLanguage> getUserLanguage() {
		return userLanguage;
	}

	public void setUserLanguage(List<UserLanguage> userLanguage) {
		this.userLanguage = userLanguage;
	}

	@Override
	public String toString() {
		return "Language [id=" + id + ", language=" + language + ", userLanguage=" + userLanguage + "]";
	}

}
