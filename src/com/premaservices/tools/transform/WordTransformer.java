package com.premaservices.tools.transform;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hwpf.HWPFDocumentCore;
import org.apache.poi.hwpf.converter.WordToFoConverter;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.converter.WordToHtmlUtils;
import org.w3c.dom.Document;

public class WordTransformer {
	
	private File word = null;
	
	public WordTransformer (File word) throws IOException  {	
		
		if (!word.exists()) throw new FileNotFoundException("File " + word.getPath() + " doesn't exist.");
		if (!word.isFile()) throw new FileNotFoundException("File " + word.getPath() + " is not a file.");
		if (!word.canRead()) throw new IOException("Cannot read file " + word.getPath());
		
		String filename = word.getName();
		if (!filename.trim().endsWith(".doc")) {
			throw new IllegalArgumentException("It's not a *.doc file.");
		}
		
		this.word = word;		
	}
	
	public void transform (WordCommand.Format f, OutputStream out) throws IOException, ParserConfigurationException, TransformerException {
		
		switch (f) {
			case HTML:
				transformToHtml(out); break;	
			case FO:
				transformToXslFo(out);
				break;
			default: 
				throw new UnsupportedOperationException (f.toString());
		}	
		
	}
	
	private void transformToHtml (OutputStream out) throws IOException, ParserConfigurationException, TransformerException {
	
		HWPFDocumentCore wordDocument = WordToHtmlUtils.loadDoc(new FileInputStream(word));
		WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
			DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument()
	    );
	    wordToHtmlConverter.processDocument(wordDocument);
	    Document htmlDocument = wordToHtmlConverter.getDocument();

	    DOMSource domSource = new DOMSource(htmlDocument);
	    StreamResult streamResult = new StreamResult(out);

	    TransformerFactory tf = TransformerFactory.newInstance();
	    Transformer serializer = tf.newTransformer();
	    serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	    serializer.setOutputProperty(OutputKeys.INDENT, "yes");
	    serializer.setOutputProperty(OutputKeys.METHOD, "html");
	    serializer.transform(domSource, streamResult);
		out.flush();	    
		out.close();
	}
	
	private void transformToXslFo (OutputStream out) throws IOException, ParserConfigurationException, TransformerException {
		
		HWPFDocumentCore wordDocument = WordToHtmlUtils.loadDoc(new FileInputStream(word));
		WordToFoConverter wordToFoConverter = new WordToFoConverter(
			DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument()
	    );
	    wordToFoConverter.processDocument(wordDocument);
	    Document foDocument = wordToFoConverter.getDocument();

	    DOMSource domSource = new DOMSource(foDocument);
	    StreamResult streamResult = new StreamResult(out);

	    TransformerFactory tf = TransformerFactory.newInstance();
	    Transformer serializer = tf.newTransformer();
	    serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	    serializer.setOutputProperty(OutputKeys.INDENT, "yes");
	    serializer.setOutputProperty(OutputKeys.METHOD, "xml");
	    serializer.transform(domSource, streamResult);
		out.flush();	    
		out.close();
	}

	public File getWord() {
		return word;
	}	

}
