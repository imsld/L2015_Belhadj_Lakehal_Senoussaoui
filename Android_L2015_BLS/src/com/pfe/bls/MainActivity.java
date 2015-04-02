package com.pfe.bls;


import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

@SuppressLint("SdCardPath")
public class MainActivity extends Activity {

	static String nom_application,api,string_messagerie,string_repertoire,string_calculatrice,string_calendrier,string_mms;
	static String string_phone,string_gallery,description_application,String_IP;

	
	
	public final static String PAR_KEY = "com.pfe.bls.par";

	HistoryFileManager HistFile = new HistoryFileManager();

	Button btn_newApp;
	Button btn_clearApp;
	Button btn_HistApp;
	Button btn_saveApp;
	Button btn_createApp;
	Button btn_go_to_mail;
	
	EditText edit_AppName;
	EditText edit_AppDescr;
    EditText edit_ip;
	RadioGroup rb_Group_API;
	RadioButton rb_ApiLocal;
	RadioButton rb_Api18;
	RadioButton rb_Api19;
	RadioButton rb_Api20;

	CheckBox cb_Calc;
	CheckBox cb_Cal;
	CheckBox cb_Msg;
	CheckBox cb_Rep;
	CheckBox cb_Mms;
	CheckBox cb_Appel;
	CheckBox cb_Galerie;

	String myVersion;
	int sdkVersion;
	
	int wait=1;

	int operation = 0; // operation = 0 : nouvelle ou initialisation, operation
						// = CurrentID : Click Sur sauvegarde ou création

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initInterface();
		addButtonClickListner();
		HistFile.initXMLDocument();
		if (HistFile.getCountApplication() == 0)
			btn_HistApp.setEnabled(false);
		else
			btn_HistApp.setEnabled(true);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// check if the request code is same as what is passed here it is 2
		if (resultCode != 0) {
			List<String> listInfo = new ArrayList<String>();
			HistFile.setCurrentID(resultCode);
			listInfo = HistFile.getCurrentApplication();

			edit_AppName.setText(listInfo.get(1));

			if (listInfo.get(2).compareTo("18")== 0)
				rb_Api18.setChecked(true);
			else if (listInfo.get(2).compareTo("19")== 0)
				rb_Api19.setChecked(true);
			else if (listInfo.get(2).compareTo("20")== 0)
				rb_Api20.setChecked(true);
			else {
				rb_ApiLocal.isChecked();
				rb_ApiLocal.setText(getAPIVersion());
			}

			edit_AppDescr.setText(listInfo.get(3));

			if (listInfo.get(4).compareTo("0")!= 0)
				cb_Calc.setChecked(true);
			else
				cb_Calc.setChecked(false);

			if (listInfo.get(5).compareTo("0")!= 0)
				cb_Cal.setChecked(true);
			else
				cb_Cal.setChecked(false);

			if (listInfo.get(6).compareTo("0")!= 0)
				cb_Msg.setChecked(true);
			else
				cb_Msg.setChecked(false);

			if (listInfo.get(7).compareTo("0")!= 0)
				cb_Rep.setChecked(true);			
			else
			cb_Rep.setChecked(false);

			if (listInfo.get(8).compareTo("0")!= 0)
				cb_Mms.setChecked(true);
			else
				cb_Mms.setChecked(false);

			if (listInfo.get(9).compareTo("0")!= 0)
				cb_Appel.setChecked(true);
			else
				cb_Appel.setChecked(false);

			if (listInfo.get(10).compareTo("0")!= 0)
				cb_Galerie.setChecked(true);
			else
				cb_Galerie.setChecked(false);
		}
	}

	private void addButtonClickListner() {
		btn_newApp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				edit_AppName.setText("");
				edit_AppDescr.setText("");
				rb_ApiLocal.setText(getAPIVersion());
				rb_ApiLocal.setActivated(true);
				cb_Calc.setChecked(false);
				cb_Cal.setChecked(false);
				cb_Msg.setChecked(false);
				cb_Rep.setChecked(false);
				cb_Mms.setChecked(false);
				cb_Appel.setChecked(false);
				cb_Galerie.setChecked(false);
			}
		});

		btn_clearApp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Getting device OS Version
				rb_ApiLocal.setText(getAPIVersion());
				rb_ApiLocal.setActivated(true);

				operation = 0;
			}
		});

		btn_HistApp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myintent = new Intent(v.getContext(),
						HistoriqueActivity.class);
				Bundle mBundle = new Bundle();

				HistFile.putMenuItems();
				mBundle.putParcelable(PAR_KEY, HistFile);
				myintent.putExtras(mBundle);

				startActivityForResult(myintent, operation);
			}
		});

		btn_saveApp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				List<String> listInfo = new ArrayList<String>();
				String dateCreation, heureCreation;
				dateCreation = new SimpleDateFormat("dd/MM/yyyy").format(System
						.currentTimeMillis());
				heureCreation = new SimpleDateFormat("HH:mm:ss").format(System
						.currentTimeMillis());

				listInfo.add(dateCreation + "," + heureCreation);

				listInfo.add(edit_AppName.getText().toString());

				if (rb_ApiLocal.isChecked())
					listInfo.add(Integer.toString(sdkVersion));
				if (rb_Api18.isChecked())
					listInfo.add("18");
				if (rb_Api19.isChecked())
					listInfo.add("19");
				if (rb_Api20.isChecked())
					listInfo.add("20");

				listInfo.add(edit_AppDescr.getText().toString());

				if (cb_Calc.isChecked())
					listInfo.add(HistFile.KEY_CALCULATRICE);
				else
					listInfo.add("0");

				if (cb_Cal.isChecked())
					listInfo.add(HistFile.KEY_CALENDRIER);
				else
					listInfo.add("0");

				if (cb_Msg.isChecked())
					listInfo.add(HistFile.KEY_MESSAGERIE);
				else
					listInfo.add("0");

				if (cb_Rep.isChecked())
					listInfo.add(HistFile.KEY_REPARTOIRE);
				else
					listInfo.add("0");

				if (cb_Mms.isChecked())
					listInfo.add(HistFile.KEY_LOCALISATION);
				else
					listInfo.add("0");

				if (cb_Appel.isChecked())
					listInfo.add(HistFile.KEY_APPEL);
				else
					listInfo.add("0");

				if (cb_Galerie.isChecked())
					listInfo.add(HistFile.KEY_CREDIT);
				else
					listInfo.add("0");

				HistFile.addApplication(listInfo);

				if (HistFile.getCountApplication() == 0)
					btn_HistApp.setEnabled(false);
				else
					btn_HistApp.setEnabled(true);

				operation = HistFile.getCurrentID();
			}
		});

		btn_createApp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				wait=1;
				launchRingDialog();
				nom_application = edit_AppName.getText().toString();
				description_application = edit_AppDescr.getText().toString();
				String_IP = edit_ip.getText().toString();
				if(cb_Calc.isChecked())string_calculatrice="1"; else string_calculatrice="0";
				if(cb_Cal.isChecked())string_calendrier="1"; else string_calendrier ="0";
				if(cb_Msg.isChecked())string_messagerie="1"; else string_messagerie="0";
				if(cb_Rep.isChecked())string_repertoire="1"; else string_repertoire = "0";
				if(cb_Mms.isChecked())string_mms="1"; else string_mms="0";
				if(cb_Appel.isChecked())string_phone="1"; else string_phone = "0";
				if(cb_Galerie.isChecked())string_gallery="1"; else string_gallery = "0";
				if(rb_ApiLocal.isChecked())api=Integer.toString(sdkVersion);
				else if (rb_Api18.isChecked())api="18";
				     else if(rb_Api19.isChecked())api="19";
				           else if(rb_Api20.isChecked())api="20";
				Xml_a_envoyer.cree();
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							EnvoyerFichierXml.lancer();
							
							Recevoire_apk_nv_app();
						} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}).start();
			}
		});
		
	
		btn_go_to_mail.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View v) {
 
			    Intent intent3 = new Intent(MainActivity.this, Act3.class);
                            startActivity(intent3);   
 
			}
 
		});
		
	}

	private void initInterface() {
		btn_newApp = (Button) findViewById(R.id.btnNewApplication);
		btn_clearApp = (Button) findViewById(R.id.btnClearFields);
		btn_HistApp = (Button) findViewById(R.id.btnShowHistory);
		btn_saveApp = (Button) findViewById(R.id.btnSaveField);
		btn_createApp = (Button) findViewById(R.id.btnCreatApplication);
		btn_go_to_mail = (Button) findViewById(R.id.mail);
		
		
		edit_AppName = (EditText) findViewById(R.id.editTextAppName);
		edit_AppDescr = (EditText) findViewById(R.id.editTextAppDescr);
		edit_ip = (EditText) findViewById(R.id.editTextip);

		rb_Group_API = (RadioGroup) findViewById(R.id.radioGroupAPI);
		rb_ApiLocal = (RadioButton) findViewById(R.id.radioApiLocal);
		rb_Api18 = (RadioButton) findViewById(R.id.radioApi18);
		rb_Api19 = (RadioButton) findViewById(R.id.radioApi19);
		rb_Api20 = (RadioButton) findViewById(R.id.radioApi20);

		cb_Calc = (CheckBox) findViewById(R.id.checkBoxCalc);
		cb_Cal = (CheckBox) findViewById(R.id.checkBoxCal);
		cb_Msg = (CheckBox) findViewById(R.id.checkBoxMsg);
		cb_Rep = (CheckBox) findViewById(R.id.checkBoxRep);
		cb_Mms = (CheckBox) findViewById(R.id.checkBoxMms);
		cb_Appel = (CheckBox) findViewById(R.id.checkBoxAppel);
		cb_Galerie = (CheckBox) findViewById(R.id.checkBoxGalerie);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private CharSequence getAPIVersion() {
		// Getting device OS Version
		myVersion = android.os.Build.VERSION.RELEASE; // e.g. myVersion := "1.6"
		sdkVersion = android.os.Build.VERSION.SDK_INT; // e.g. sdkVersion := 8;
		return "API " + sdkVersion + ", version " + myVersion + " (local API)";
	}
	
	private void launchRingDialog() {
		final ProgressDialog ringProgressDialog = ProgressDialog.show(MainActivity.this, "Please wait ...",	"Downloading application ...", true);
		ringProgressDialog.setCancelable(true);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					
					
					while(wait==1);
				} catch (Exception e) {

				}
				ringProgressDialog.dismiss();
				
			}
		}).start();
	}

private void Recevoire_apk_nv_app() {
		
		new Thread(new Runnable() {
	        @Override
	        public void run() {
	        	//
				//on supprime l'ancien apk
				suprim_apk("/data/data/com.pfe.bls/app.apk");
				
	        	recevoire_apk_nv_app.lancer();
	        	//ajouter la permission pour éxécuter le apk 
			    Chmod.lancer("/data/data/com.pfe.bls/app.apk");
			    wait=0;
			    //lancer l'instalation du apk
				installer_apk("/data/data/com.pfe.bls/app.apk");
				
				
	        }
	    }).start();
	}



private void suprim_apk(String path){
	File file = new File(path);
	if(file.exists()){
	boolean supp = file.delete();}
}

private void installer_apk(String path){
	
	
	Intent intent = new Intent(Intent.ACTION_VIEW);
	intent.setDataAndType(Uri.fromFile(new File(path)),"application/vnd.android.package-archive");
	startActivity(intent);
	
}
}
