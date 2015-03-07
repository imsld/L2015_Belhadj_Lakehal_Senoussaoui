package com.example.android;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class HistoryFileManager {

	String filePath = "/data/data/com.example.android/historique.xml";

	public boolean checkHistoryFile() {
		File xmlFile = new File(filePath);		
		if (xmlFile.isFile())
			return true;
		else
			return false;
	}

	public void createEmptyRacine() {

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			Element rootElement = doc.createElement("Applications");
			doc.appendChild(rootElement);

			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			// for pretty print
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);

			File xmlFile = new File(filePath);
			xmlFile.getPath();
			
			// write to console or file
			StreamResult console = new StreamResult(System.out);
			StreamResult file = new StreamResult(xmlFile);
			//
			// write data
			transformer.transform(source, console);
			transformer.transform(source, file);
			
			//transformer.transform(xmlFile.getPath(), console);
			
			System.out.println(file.getSystemId()+" "+xmlFile.getPath());

			System.out.println("DONE");			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getTotalApplication(){
		return 0;
	}
	
	public void addApplication(){
		
	}
	
	public void getApplication(){
		
	}

}
