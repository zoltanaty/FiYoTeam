package com.fiyoteam.model.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserLanguageResponse implements Serializable{
	
	private static final long serialVersionUID = -3049148222226326302L;
	
	private List<Language> languages;
	
	public UserLanguageResponse() {
		super();
		languages = new ArrayList<>();
	}

	public List<Language> getLanguages() {
		return languages;
	}

	public void addLanguage(String language, int level){
		languages.add(new  Language(language, level));
	}
	
	@Override
	public String toString() {
		return "Languages [" + languages + "]";
	}

	public class Language{
		private String language;
		private int level;
		
		public Language(String language, int level) {
			super();
			this.language = language;
			this.level = level;
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
			return "Language [language=" + language + ", level=" + level + "]";
		}
		
	}
}
