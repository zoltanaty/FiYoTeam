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

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@Entity
@Table(name = "collaboration")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Collaboration  implements Serializable {

	private static final long serialVersionUID = 6725076752677532327L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id", unique=true, nullable=false)
	private int id;
	
	@ManyToOne
    @JoinColumn(name = "user_id")  
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "project_id")  
	private Project project;
	
	@ManyToOne
	@JoinColumn(name = "owner_id")  
	private User owner;
	
    @Column(name = "accepted")
	private boolean accepted;
    
    public Collaboration(){
    	
    }

	public Collaboration(int id, User user, Project project, User owner, boolean accepted) {
		super();
		this.id = id;
		this.user = user;
		this.project = project;
		this.owner = owner;
		this.accepted = accepted;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	@Override
	public String toString() {
		return "Collaboration [id=" + id + ", user=" + user.getId() + ", project=" + project.getId() + ", owner=" + owner.getId()
				+ ", accepted=" + accepted + "]";
	}
    
}
