package com.fiyoteam.model.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserSkillDTO implements Serializable{
	
	private static final long serialVersionUID = 7786084067507214571L;
	
	private List<Skill> skills;
	
	public UserSkillDTO() {
		super();
		skills = new ArrayList<>();
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void addSkill(int id, String skill, int level){
		skills.add(new  Skill(id, skill, level));
	}
	
	@Override
	public String toString() {
		return "Languages [" + skills + "]";
	}

	public static class Skill{
		private int id;
		private String skill;
		private int level;
		
		public Skill(){
			super();
		}
		
		public Skill(int id, String skill, int level) {
			super();
			this.id = id;
			this.skill = skill;
			this.level = level;
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

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		@Override
		public String toString() {
			return "Skill [id=" + id + ", skill=" + skill + ", level=" + level + "]";
		}
		
	}
}
