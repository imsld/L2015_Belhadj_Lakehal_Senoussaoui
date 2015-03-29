package com.pfe.bls;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint("SdCardPath")
public class HistoryFileManager implements Parcelable {

	static final String KEY_RACINE = "Applications";
	static final String KEY_ID = "ID";
	static final String KEY_APPLICATION = "Application";
	static final String KEY_DATE_CREATION = "Date_creation";
	static final String KEY_NOM_APPLICATION = "Nom";
	static final String KEY_VERSION_SDK = "Version_SDK";
	static final String KEY_DESCRIPTION = "Description";
	static final String KEY_CALCULATRICE = "Calculatrice";
	static final String KEY_CALENDRIER = "Calendrier";
	static final String KEY_MESSAGERIE = "Messagerie";
	static final String KEY_REPARTOIRE = "Repartoire";
	static final String KEY_LOCALISATION = "Localisation";
	static final String KEY_APPEL = "Appel";
	static final String KEY_CREDIT = "Credit";

	private String filePath = "/data/data/com.pfe.bls/historique.xml";
	private File xmlFile = null;

	public Document doc = null;
	private DocumentBuilder dBuilder;
	private DocumentBuilderFactory dbFactory;

	private int CurrentID = -1;

	public ArrayList<HashMap<String, String>> menuItems;

	public static final Creator<HistoryFileManager> CREATOR = new Creator<HistoryFileManager>() {
		@Override
		public HistoryFileManager createFromParcel(Parcel source) {
			HistoryFileManager mHistFile = new HistoryFileManager();
			mHistFile.filePath = source.readString();
			mHistFile.menuItems = (ArrayList<HashMap<String, String>>) source
					.readSerializable();
			return mHistFile;
		}

		@Override
		public HistoryFileManager[] newArray(int size) {
			return new HistoryFileManager[size];
		}
	};

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
		arg0.writeString(filePath);
		arg0.writeSerializable(menuItems);
	}

	private boolean checkHistoryFile() {
		File xmlFileTest = new File(filePath);
		if (xmlFileTest.isFile())
			return true;
		else
			return false;
	}

	private void initXMLFile() {
		if (xmlFile == null)
			xmlFile = new File(filePath);
	}

	private void createXMLDocument() {

		dbFactory = DocumentBuilderFactory.newInstance();
		try {

			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.newDocument();
			Element racine = doc.createElement(KEY_RACINE);
			doc.appendChild(racine);

			writeInFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void writeInFile() {
		// for output to file, console
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer;

		try {
			transformer = transformerFactory.newTransformer();
			// for pretty print
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);

			// write to console or file
			StreamResult console = new StreamResult(System.out);
			StreamResult file = new StreamResult(new File(filePath));

			// write data
			transformer.transform(source, console);
			transformer.transform(source, file);

			xmlFile = new File(filePath);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void ParseDocument() {
		if (doc == null) {
			dbFactory = DocumentBuilderFactory.newInstance();
			try {
				dBuilder = dbFactory.newDocumentBuilder();
				doc = dBuilder.parse(xmlFile);
				doc.getDocumentElement().normalize();

				doc.getDocumentElement().normalize();
				NodeList nList = doc.getElementsByTagName("Application");
				Element nNode = (Element) nList.item(nList.getLength());
				CurrentID = Integer.parseInt(nNode.getAttribute(KEY_ID));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private int getID() {
		int maxID = 0;

		if (getCountApplication() == 0)
			return maxID+1;
		else {
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("Application");
			for (int i = 0; i < nList.getLength(); i++) {
				Element nNode = (Element) nList.item(i);
				int localID = Integer.parseInt(nNode.getAttribute(KEY_ID));
				if (localID > maxID)
					maxID = localID;
			}
			return maxID + 1;
		}
	}

	public void initXMLDocument() {
		if (!checkHistoryFile()) {
			createXMLDocument();
		} else
			initXMLFile();

		ParseDocument();
	}

	public int getCurrentID() {
		return CurrentID;
	}

	public int getCountApplication() {
		Element item = doc.getDocumentElement();
		NodeList listApplication = item.getElementsByTagName("Application");
		return listApplication.getLength();
	}

	public void addApplication(List<String> listInfo) {
		doc.getDocumentElement().normalize();

		CurrentID = getID();
		
		Element racine = doc.getDocumentElement();
		Element appliElement = doc.createElement(KEY_APPLICATION);
		racine.appendChild(appliElement);

		
		appliElement.setAttribute(KEY_ID, Integer.toString(CurrentID));

		Element newChild_0 = doc.createElement(KEY_DATE_CREATION);
		newChild_0.setTextContent(listInfo.get(0));
		appliElement.appendChild(newChild_0);

		Element newChild_1 = doc.createElement(KEY_NOM_APPLICATION);
		newChild_1.setTextContent(listInfo.get(1));
		appliElement.appendChild(newChild_1);

		Element newChild_2 = doc.createElement(KEY_VERSION_SDK);
		newChild_2.setTextContent(listInfo.get(2));
		appliElement.appendChild(newChild_2);

		Element newChild_3 = doc.createElement(KEY_DESCRIPTION);
		newChild_3.setTextContent(listInfo.get(3));
		appliElement.appendChild(newChild_3);

		Element newChild_4 = doc.createElement(KEY_CALCULATRICE);
		newChild_4.setTextContent(listInfo.get(4));
		appliElement.appendChild(newChild_4);

		Element newChild_5 = doc.createElement(KEY_CALENDRIER);
		newChild_5.setTextContent(listInfo.get(5));
		appliElement.appendChild(newChild_5);

		Element newChild_6 = doc.createElement(KEY_MESSAGERIE);
		newChild_6.setTextContent(listInfo.get(6));
		appliElement.appendChild(newChild_6);

		Element newChild_7 = doc.createElement(KEY_REPARTOIRE);
		newChild_7.setTextContent(listInfo.get(7));
		appliElement.appendChild(newChild_7);

		Element newChild_8 = doc.createElement(KEY_LOCALISATION);
		newChild_8.setTextContent(listInfo.get(8));
		appliElement.appendChild(newChild_8);

		Element newChild_9 = doc.createElement(KEY_APPEL);
		newChild_9.setTextContent(listInfo.get(9));
		appliElement.appendChild(newChild_9);

		Element newChild_10 = doc.createElement(KEY_CREDIT);
		newChild_10.setTextContent(listInfo.get(10));
		appliElement.appendChild(newChild_10);

		doc.getDocumentElement().normalize();
		writeInFile();
	}

	public void deleteCurentApplication(){
		
	}
	public List<String> getCurrentApplication() {
		List<String> listInfo = new ArrayList<String>();
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("Application");
		for (int i = 0; i < nList.getLength(); i++) {
			Element nNode = (Element) nList.item(i);
			if (Integer.parseInt(nNode.getAttribute(KEY_ID)) == CurrentID) {
				listInfo.add(nNode.getElementsByTagName(KEY_DATE_CREATION)
						.item(0).getTextContent());
				listInfo.add(nNode.getElementsByTagName(KEY_NOM_APPLICATION)
						.item(0).getTextContent());
				listInfo.add(nNode.getElementsByTagName(KEY_VERSION_SDK)
						.item(0).getTextContent());
				listInfo.add(nNode.getElementsByTagName(KEY_DESCRIPTION)
						.item(0).getTextContent());
				listInfo.add(nNode.getElementsByTagName(KEY_CALCULATRICE)
						.item(0).getTextContent());
				listInfo.add(nNode.getElementsByTagName(KEY_CALENDRIER).item(0)
						.getTextContent());
				listInfo.add(nNode.getElementsByTagName(KEY_MESSAGERIE).item(0)
						.getTextContent());
				listInfo.add(nNode.getElementsByTagName(KEY_REPARTOIRE).item(0)
						.getTextContent());
				listInfo.add(nNode.getElementsByTagName(KEY_LOCALISATION)
						.item(0).getTextContent());
				listInfo.add(nNode.getElementsByTagName(KEY_APPEL).item(0)
						.getTextContent());
				listInfo.add(nNode.getElementsByTagName(KEY_CREDIT).item(0)
						.getTextContent());
			}
		}
		return listInfo;
	}

	public void putMenuItems() {
		menuItems = new ArrayList<HashMap<String, String>>();
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("Application");

		for (int i = 0; i < nList.getLength(); i++) {
			// creating new HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			Element nNode = (Element) nList.item(i);
			map.put(KEY_ID, nNode.getAttribute(KEY_ID));
			map.put(KEY_DATE_CREATION,
					nNode.getElementsByTagName(KEY_DATE_CREATION).item(0)
							.getTextContent());
			map.put(KEY_NOM_APPLICATION,
					nNode.getElementsByTagName(KEY_NOM_APPLICATION).item(0)
							.getTextContent());
			map.put(KEY_VERSION_SDK, nNode
					.getElementsByTagName(KEY_VERSION_SDK).item(0)
					.getTextContent());
			map.put(KEY_DESCRIPTION, nNode
					.getElementsByTagName(KEY_DESCRIPTION).item(0)
					.getTextContent());
			map.put(KEY_CALCULATRICE,
					nNode.getElementsByTagName(KEY_CALCULATRICE).item(0)
							.getTextContent());
			map.put(KEY_CALENDRIER, nNode.getElementsByTagName(KEY_CALENDRIER)
					.item(0).getTextContent());
			map.put(KEY_MESSAGERIE, nNode.getElementsByTagName(KEY_MESSAGERIE)
					.item(0).getTextContent());
			map.put(KEY_REPARTOIRE, nNode.getElementsByTagName(KEY_REPARTOIRE)
					.item(0).getTextContent());
			map.put(KEY_LOCALISATION,
					nNode.getElementsByTagName(KEY_LOCALISATION).item(0)
							.getTextContent());
			map.put(KEY_APPEL, nNode.getElementsByTagName(KEY_APPEL).item(0)
					.getTextContent());
			map.put(KEY_CREDIT, nNode.getElementsByTagName(KEY_CREDIT).item(0)
					.getTextContent());
			// adding HashList to ArrayList
			menuItems.add(map);
		}
	}
}
