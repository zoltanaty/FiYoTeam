package com.fiyoteam.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name = "project")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project implements Serializable{

	private static final long serialVersionUID = 4037369589418544477L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id", unique=true, nullable=false)
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "created_at")
	private Date createdAt;
	
	@ManyToOne()
    @JoinColumn(name = "user_id") 
	private User user;
	
	@OneToMany(mappedBy = "project")
	private List<ProjectSkill> projectSkill = new ArrayList<>();

	public Project() {
		super();
	}

	public Project(int id, String name, String description, String status, User user, Date createdAt, List<ProjectSkill> projectSkill) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.status = status;
		this.createdAt = createdAt;
		this.user = user;
		this.projectSkill = projectSkill;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@JsonIgnore 
	public List<ProjectSkill> getProjectSkill() {
		return projectSkill;
	}

	public void setProjectSkill(List<ProjectSkill> projectSkill) {
		this.projectSkill = projectSkill;
	}

	@JsonIgnore 
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", name=" + name + ", description=" + description + ", status=" + status
				+ ", createdAt=" + createdAt + ", user=" + user + ", projectSkill=" + projectSkill + "]";
	}

}
