package com.pfe.bls;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;


@SuppressLint("SdCardPath")
public class recevoire_apk_nv_app extends MainActivity {
	
	public static void lancer()  {
		
     
   
    	 Socket s;

	try {
		//temp de traitement du serveur
		Thread.sleep(30000);
	} catch (InterruptedException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
      try{
	s = new Socket(String_IP, 4000);

	ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
	
	byte [] buffer = (byte[]) ois.readObject();
	FileOutputStream fos = new FileOutputStream("/data/data/com.pfe.bls/app.apk");
	fos.write(buffer);
	fos.close();
	

      }catch (ClassNotFoundException e){
        e.printStackTrace();	
    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      }


	}

