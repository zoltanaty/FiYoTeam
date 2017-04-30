package com.fiyoteam.model.response;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fiyoteam.model.Project;
import com.fiyoteam.model.response.UserSkillResponse.Skill;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProjectResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Project project;
	private List<Skill> skills;
	private String authorName;
	private Integer authorId;
	private String createdAt;
	private SimpleDateFormat format;  
	
	public UserProjectResponse() {
		super();
		this.format = new SimpleDateFormat("yyyy-MM-dd");
	}

	public UserProjectResponse(Project project, List<Skill> skills, String authorName, Integer authorId,
			String createdAt) {
		super();
		this.project = project;
		this.skills = skills;
		this.authorName = authorName;
		this.authorId = authorId;
		this.createdAt = createdAt;
		this.format = new SimpleDateFormat("dd-MM-yyyy at hh:mm:ss");
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
	
	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = this.format.format(createdAt);
	}

	@Override
	public String toString() {
		return "UserProjectResponse [project=" + project + ", skill=" + skills + "]";
	}
	
}
