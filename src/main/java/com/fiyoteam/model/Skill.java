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
@Table(name = "skill")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Skill implements Serializable{

	private static final long serialVersionUID = -5152335769421379675L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id", unique=true, nullable=false)
	private int id;
	
	@Column(name = "skill")
	private String skill;
	
	@OneToMany(mappedBy = "skill")
	private List<UserSkill> userSkill = new ArrayList<>();
	
	@OneToMany(mappedBy = "skill")
	private List<ProjectSkill> projectSkill = new ArrayList<>();
	
	public Skill(){
		super();
	}

	public Skill(int id, String skill, List<UserSkill> userSkill, List<ProjectSkill> projectSkill) {
		super();
		this.id = id;
		this.skill = skill;
		this.userSkill = userSkill;
		this.projectSkill = projectSkill;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	@JsonIgnore 
	public List<UserSkill> getUserSkill() {
		return userSkill;
	}

	public void setUserSkill(List<UserSkill> userSkill) {
		this.userSkill = userSkill;
	}
	
	@JsonIgnore 
	public List<ProjectSkill> getProjectSkill() {
		return projectSkill;
	}

	public void setProjectSkill(List<ProjectSkill> projectSkill) {
		this.projectSkill = projectSkill;
	}

	@Override
	public String toString() {
		return "Skill [id=" + id + ", skill=" + skill + ", userSkill=" + userSkill + ", projectSkill=" + projectSkill
				+ "]";
	}
}
