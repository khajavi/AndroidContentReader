package com.elearnever.android;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.elearnever.android.content.ContentHandler;
//import com.elearnever.android.content.XmlContentHandler;

public class GetContent implements Serializable {

	/**
	 *  
	 */
	private static final long serialVersionUID = 1L;
	private InputStream inputStream;
	public ContentHandler handler;

	public GetContent(InputStream is) throws ParserConfigurationException,
			SAXException, IOException {
		this.inputStream = is;

		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();

		handler = new ContentHandler();

		saxParser.parse(inputStream, handler);
	}
}
