package com.example.android;

import java.io.File;

public class HistoryFileManager {

	String filePath = "historique.xml";

	public void createHistoryFile() {
	}

	public boolean checkHistoryFile() {
		File xmlFile = new File(filePath);
		if (xmlFile.isFile())
			return true;
		else
			return false;
	}

}
