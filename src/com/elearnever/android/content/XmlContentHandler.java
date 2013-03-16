//package com.elearnever.android.content;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//
//import org.xml.sax.Attributes;
//import org.xml.sax.SAXException;
//import org.xml.sax.helpers.DefaultHandler;
//
//import android.util.Log;
//
//public class XmlContentHandler extends DefaultHandler {
//
//	private Course course;
//	private Chapter chapter;
//	private Chapters chapters;
//	private Pages pages;
//	private Page page;
//
//	private boolean inChapter = false;
//	private boolean inPage = false;
//
//	private StringBuilder content;
//	private StringBuilder builder;
//
//	public XmlContentHandler() {
//		chapters = new Chapters();
//		pages = new Pages();
//		content = new StringBuilder();
//		builder = new StringBuilder();
//	}
//
//	public void startElement(String uri, String localName, String qName,
//			Attributes attributes) throws SAXException {
//		content = new StringBuilder();
//
//		if (qName.equalsIgnoreCase("course")) {
//			course = new Course();
//		} else if (qName.equalsIgnoreCase("chapter")) {
//			inChapter = true;
//			chapter = new Chapter();
//			pages = new Pages();
//		} else if (qName.equalsIgnoreCase("page")) {
//			builder = new StringBuilder();
//			inPage = true;
//			page = new Page();
//			// page.setSoundURL(attributes.getValue(0));
//		}
//
//		if (inPage) {
//			builder.append("<" + qName);
//			for (int i = 0; i < attributes.getLength(); i++) {
//				builder.append(" " + attributes.getLocalName(i) + "=\""
//						+ "file:///android_asset/content"
//						+ attributes.getValue(i) + "\"");
//			}
//			builder.append(">");
//		}
//	}
//
//	public void endElement(String uri, String localName, String qName)
//			throws SAXException {
//
//		if (inPage) {
//			builder.append("</" + qName + ">");
//		}
//
//		if (qName.equalsIgnoreCase("sound")) {
//			if (inPage) {
//				page.setSoundURL(content.toString());
//				Log.d("Player", "setSoundURL: " + content.toString());
//			}
//		} else if (qName.equalsIgnoreCase("title")) {
//			if (inPage) {
//				page.setTitle(content.toString());
//			} else if (inChapter) {
//				chapter.setTitle(content.toString());
//			} else {
//				course.setTitle(content.toString());
//			}
//		} else if (qName.equalsIgnoreCase("page")) {
//			page.setContent(builder.toString());
//			pages.add(page);
//			inPage = false;
//		} else if (qName.equalsIgnoreCase("chapter")) {
//			inChapter = false;
//			chapter.setPages(pages);
//			chapters.add(chapter);
//			inChapter = false;
//		} else if (qName.equalsIgnoreCase("course")) {
//			course.setChapters(chapters);
//		}
//	}
//
//	public void characters(char ch[], int start, int length)
//			throws SAXException {
//		content.append(ch, start, length);
//
//		if (inPage) {
//			builder.append(ch, start, length);
//		}
//	}
//
//	public Course getCourse() {
//		return course;
//	}
//
//	public String[] getChapters() {
//		int chapterLength = chapters.toArray().length;
//
//		String chaptersString[] = new String[chapterLength];
//		for (int i = 0; i < chapterLength; i++) {
//			chaptersString[i] = chapters.get(i).getTitle();
//		}
//
//		return chaptersString;
//	}
//
//	public String[][] getPages() {
//		int i = 0;
//		int j = 0;
//		Iterator<Chapter> chaptersItr = chapters.iterator();
//		String pagesTitles[][] = new String[chapters.toArray().length][];
//		while (chaptersItr.hasNext()) {
//			Chapter chapter = chaptersItr.next();
//			pagesTitles[i] = new String[chapter.getPages().toArray().length];
//			ArrayList<Page> page = chapter.getPages();
//			Iterator<Page> pageItr = page.iterator();
//			while (pageItr.hasNext()) {
//				Page page2 = pageItr.next();
//				pagesTitles[i][j] = page2.getTitle();
//				j++;
//			}
//			j = 0;
//			i++;
//		}
//		return pagesTitles;
//	}
//}