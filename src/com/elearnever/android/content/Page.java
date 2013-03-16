package com.elearnever.android.content;

import java.io.Serializable;

public class Page implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pageContent;
	private String title;
	private String audioURL;
	private String videoURL;
	
	public Page() {
		setContent(null);
		setTitle(null);
		setAudioURL(null);
		setVideoURL(null);
	}
	
	public void setContent(String pageContent) {
		this.pageContent = pageContent;
	}
	
	public String getContent() { 
		return pageContent;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setAudioURL(String soundURL) {
		this.audioURL = soundURL;
	}
	
	public String getAudioURL() {
		return audioURL;
	}
	
	public void setVideoURL(String videoURL) {
		this.videoURL = videoURL;
	}
	
	public String getVideoURL() {
		return videoURL;
	}
}