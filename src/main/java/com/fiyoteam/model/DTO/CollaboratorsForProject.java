package com.fiyoteam.model.DTO;

import java.io.Serializable;
import java.util.List;

import com.fiyoteam.model.Project;
import com.fiyoteam.model.User;

public class CollaboratorsForProject implements Serializable{

	private static final long serialVersionUID = 6918889677435805761L;

	private Project project;
	private List<User> collaborators;
	
	public CollaboratorsForProject(){
		
	}

	public CollaboratorsForProject(Project project, List<User> collaborators) {
		super();
		this.project = project;
		this.collaborators = collaborators;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<User> getCollaborators() {
		return collaborators;
	}

	public void setCollaborators(List<User> collaborators) {
		this.collaborators = collaborators;
	}

	@Override
	public String toString() {
		return "CollaboratorsForProject [project=" + project + ", collaborators=" + collaborators + "]";
	}
	
}
