package com.fiyoteam.model.response;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fiyoteam.model.Project;
import com.fiyoteam.model.response.UserSkillResponse.Skill;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProjectResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Project project;
	private List<Skill> skills;
	
	public UserProjectResponse() {
		super();
	}

	public UserProjectResponse(Project project, List<Skill> skills) {
		super();
		this.project = project;
		this.skills = skills;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

	@Override
	public String toString() {
		return "UserProjectResponse [project=" + project + ", skill=" + skills + "]";
	}
	
}
