package com.elearnever.android.content;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ContentHandler extends DefaultHandler implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Course course;
	private Sessions sessions;
	private Session session;
	private Pages pages;
	private Page page;

	private StringBuilder content;

	private boolean inSession = false;
	private boolean inPage = false;
	private boolean inCourse = false;

	public ContentHandler() {
		sessions = new Sessions();
		pages = new Pages();
		content = new StringBuilder();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		content = new StringBuilder();

		if (qName.equalsIgnoreCase("course")) {
			course = new Course();
			inCourse = true;
		} else if (qName.equalsIgnoreCase("session")) {
			inSession = true;
			session = new Session();
			pages = new Pages();
		} else if (qName.equalsIgnoreCase("page")) {
			inPage = true;
			page = new Page();
		}
	}

	@Override
	public void characters(char ch[], int start, int length)
			throws SAXException {
		content.append(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (inPage) {
			if (qName.equalsIgnoreCase("content")) {
				page.setContent(content.toString());
			} else if (qName.equalsIgnoreCase("audio")) {
				page.setAudioURL(content.toString());
			} else if (qName.equalsIgnoreCase("video)")) {
				page.setVideoURL(content.toString());
			} else if (qName.equalsIgnoreCase("title")) {
				page.setTitle(content.toString());
			} else if (qName.equalsIgnoreCase("page")) {
				pages.add(page);
				inPage = false;
			}
		} else if (inSession) {
			if (qName.equalsIgnoreCase("title")) {
				session.setTitle(content.toString());
			} else if (qName.equalsIgnoreCase("session")) {
				session.setPages(pages);
				sessions.add(session);
				inSession = false;
			}
		} else if (inCourse) {
			if (qName.equalsIgnoreCase("title")) {
				course.setTitle(content.toString());
			} else if (qName.equalsIgnoreCase("language")) {
				course.setLanguage(content.toString());
			} else if (qName.equalsIgnoreCase("course")) {
				course.setSessions(sessions);
			}
		}
	}

	public Course getCourse() {
		return course;
	}

	public String[] getSessions() {
		int sessionLength = sessions.toArray().length;

		String sessionsString[] = new String[sessionLength];
		for (int i = 0; i < sessionLength; i++) {
			sessionsString[i] = sessions.get(i).getTitle();
		}

		return sessionsString;
	}

	public String[][] getPages() {
		int i = 0;
		int j = 0;
		Iterator<Session> sessionsItr = sessions.iterator();
		String pagesTitles[][] = new String[sessions.toArray().length][];
		while (sessionsItr.hasNext()) {
			Session session = sessionsItr.next();
			pagesTitles[i] = new String[session.getPages().toArray().length];
			ArrayList<Page> page = session.getPages();
			Iterator<Page> pageItr = page.iterator();
			while (pageItr.hasNext()) {
				Page page2 = pageItr.next();
				pagesTitles[i][j] = page2.getTitle();
				j++;
			}
			j = 0;
			i++;
		}
		return pagesTitles;
	}

	public String contentOf(int position) {

		String content = null;

		Iterator<Session> sessionsItr = sessions.iterator();
		while (sessionsItr.hasNext()) {
			Session session = sessionsItr.next();
			ArrayList<Page> page = session.getPages();
			Iterator<Page> pageItr = page.iterator();
			while (pageItr.hasNext() && position >= 0) {
				content = pageItr.next().getContent();
				position--;
			}
		}
		return content;
	}

	public String getAudioUrlOf(int position) {

		String audioURL = null;

		Iterator<Session> sessionsItr = sessions.iterator();
		while (sessionsItr.hasNext()) {
			Session session = sessionsItr.next();
			ArrayList<Page> page = session.getPages();
			Iterator<Page> pageItr = page.iterator();
			while (pageItr.hasNext() && position >= 0) {
				audioURL = pageItr.next().getAudioURL();
				position--;
			}
		}

		return audioURL;
	}

	public int getSize() {
		int k = 0;
		Iterator<Session> sessionsItr = sessions.iterator();
		while (sessionsItr.hasNext()) {
			Session session = sessionsItr.next();
			ArrayList<Page> page = session.getPages();
			Iterator<Page> pageItr = page.iterator();
			while (pageItr.hasNext()) {
				pageItr.next();
				k++;
			}
		}
		return k;
	}

	public int getIndexOf(int sessionId, int pageId) {
		int id = -1;
		int i = 0;
		int j = 0;
		Iterator<Session> sessionsItr = sessions.iterator();
		while (sessionsItr.hasNext() && i <= sessionId) {
			Session session = sessionsItr.next();
			ArrayList<Page> page = session.getPages();
			Iterator<Page> pageItr = page.iterator();
			while (pageItr.hasNext()) {
				pageItr.next();
				id++;
				if (i == sessionId && j == pageId)
					return id;
				j++;
			}
			j = 0;
			i++;
		}
		return id;
	}
}
