//package com.elearnever.android;
//
//import java.util.ArrayList;
//
//import org.xml.sax.Attributes;
//import org.xml.sax.SAXException;
//import org.xml.sax.helpers.DefaultHandler;
//
//import com.google.common.collect.ArrayListMultimap;
//
//public class EducationalContent extends DefaultHandler {
//
//	boolean chapter = false;
//	boolean page = false;
//	int chapterNumber = 0;
//	int pageNumber = 0;
//
//	public ArrayListMultimap<String, String> courseList = ArrayListMultimap
//			.create();
//	ArrayList<String> chapers = new ArrayList<String>();
//
//	@Override
//	public void startElement(String uri, String localName, String qName,
//			Attributes attribute) throws SAXException {
//
//		if (qName.equalsIgnoreCase("chapter")) {
//			chapterNumber++;
//			chapers.add("فصل " + chapterNumber);
//		}
//
//		if (qName.equalsIgnoreCase("page")) {
//			pageNumber = pageNumber + 1;
//			courseList.put("فصل " + String.valueOf(chapterNumber), "صفحهٔ "
//					+ String.valueOf(pageNumber));
//		}
//	}
//
//
//	@Override
//	public void endElement(String uri, String localName, String qName) {
//		if (qName.equalsIgnoreCase("chapter")) {
//			pageNumber = 0;
//		}
//	}
//
//	
//
//}
