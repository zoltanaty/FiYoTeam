package com.fiyoteam.model.response;

import java.io.Serializable;
import java.util.List;

import com.fiyoteam.model.Project;
import com.fiyoteam.model.response.UserSkillResponse.Skill;

public class UserProjectResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Project project;
	private List<Skill> skill;
	
	public UserProjectResponse() {
		super();
	}

	public UserProjectResponse(Project project, List<Skill> skill) {
		super();
		this.project = project;
		this.skill = skill;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<Skill> getSkill() {
		return skill;
	}

	public void setSkill(List<Skill> skill) {
		this.skill = skill;
	}

	@Override
	public String toString() {
		return "UserProjectResponse [project=" + project + ", skill=" + skill + "]";
	}
	
}
