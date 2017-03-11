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
@Table(name = "user_skill")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSkill implements Serializable{

	private static final long serialVersionUID = -6136261065399502920L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id", unique=true, nullable=false)
	private int id;
	
	@ManyToOne()
    @JoinColumn(name = "user_id")  
	private User user;
	
	@ManyToOne()
    @JoinColumn(name = "skill_id")  
	private Skill skill;
	
	@Column(name = "level")
	private int level;
	
	public UserSkill(){
		super();
	}

	public UserSkill(int id, User user, Skill skill, int level) {
		super();
		this.id = id;
		this.user = user;
		this.skill = skill;
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

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "UserLanguage [id=" + id + ", user=" + user + ", language=" + skill.getSkill() + ", level=" + level + "]";
	}
	
}
