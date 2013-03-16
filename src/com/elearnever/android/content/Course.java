package com.elearnever.android.content;

import java.io.Serializable;

public class Course implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private String language;
	private Sessions sessions ;

	
	public Course() {
		setTitle(null);
		setLanguage(null);
		setSessions(null);
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setSessions(Sessions sessions) {
		this.sessions = sessions;
	}
	
	public Sessions getSessions() {
		return sessions;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public String getLanguage() {
		return language;
	}
}