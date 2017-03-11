package com.fiyoteam.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name = "user_language")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserLanguage implements Serializable{

	private static final long serialVersionUID = -8198222964635990054L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id", unique=true, nullable=false)
	private int id;
	
	@ManyToOne()
    @JoinColumn(name = "user_id")  
	private User user;
	
	@ManyToOne()
    @JoinColumn(name = "language_id")  
	private Language language;
	
	@Column(name = "level")
	private int level;
	
	public UserLanguage(){
		super();
	}

	public UserLanguage(int id, User user, Language language, int level) {
		super();
		this.id = id;
		this.user = user;
		this.language = language;
		this.level = level;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@JsonIgnore 
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "UserLanguage [id=" + id + ", user=" + user + ", language=" + language.getLanguage() + ", level=" + level + "]";
	}
	
}
