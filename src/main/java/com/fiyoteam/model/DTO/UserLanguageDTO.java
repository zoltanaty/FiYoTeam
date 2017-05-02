package com.fiyoteam.model.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserLanguageDTO implements Serializable{
	
	private static final long serialVersionUID = -3049148222226326302L;
	
	private List<Language> languages;
	
	public UserLanguageDTO() {
		super();
		languages = new ArrayList<>();
	}

	public List<Language> getLanguages() {
		return languages;
	}

	public void addLanguage(int id, String language, int level){
		languages.add(new  Language(id, language, level));
	}
	
	@Override
	public String toString() {
		return "Languages [" + languages + "]";
	}

	public static class Language{
		private int id;
		private String language;
		private int level;
		
		public Language(){
			super();
		}
		
		public Language(int id, String language, int level) {
			super();
			this.id = id;
			this.language = language;
			this.level = level;
		}
		
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getLanguage() {
			return language;
		}

		public void setLanguage(String language) {
			this.language = language;
		}

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		@Override
		public String toString() {
			return "Language [id=" + id + ", language=" + language + ", level=" + level + "]";
		}

	}
}
