package com.elearnever.android.content;

import java.io.Serializable;

public class Session implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Pages pages;
	private String title;

	public Session() {
		setPages(null);
		setTitle(null);
	}

	public void setPages(Pages pages) {
		this.pages = pages;
	}

	public Pages getPages() {
		return pages;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}