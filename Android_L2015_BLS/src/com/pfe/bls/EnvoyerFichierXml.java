package com.pfe.bls;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class EnvoyerFichierXml extends MainActivity {
	public static void lancer() throws UnknownHostException, IOException{
		
		
		
		 Socket socket = null;

		    socket = new Socket(String_IP, 61000);

		    File file = new File("/data/data/com.pfe.bls/informations.xml");
		    // Get the size of the file
		    long length = file.length();
		    if (length > Integer.MAX_VALUE) {
		        System.out.println("File is too large.");
		    }
		    byte[] bytes = new byte[(int) length];
		    FileInputStream fis = new FileInputStream(file);
		    BufferedInputStream bis = new BufferedInputStream(fis);
		    BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());

		    int count;

		    while ((count = bis.read(bytes)) > 0) {
		        out.write(bytes, 0, count);
		    }

		    out.flush();
		    out.close();
		    fis.close();
		    bis.close();
		    socket.close();

		}
}
