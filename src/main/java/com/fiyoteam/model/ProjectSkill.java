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
@Table(name = "project_skill")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectSkill implements Serializable{

	private static final long serialVersionUID = -794959948422837062L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id", unique=true, nullable=false)
	private int id;
	
	@ManyToOne()
    @JoinColumn(name = "project_id")  
	private Project project;
	
	@ManyToOne()
    @JoinColumn(name = "skill_id")  
	private Skill skill;

	public ProjectSkill() {
		super();
	}

	public ProjectSkill(int id, Project project, Skill skill) {
		super();
		this.id = id;
		this.project = project;
		this.skill = skill;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@JsonIgnore 
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	@Override
	public String toString() {
		return "ProjectSkill [id=" + id + ", project=" + project + ", skill=" + skill.getSkill() + "]";
	}
	
}
