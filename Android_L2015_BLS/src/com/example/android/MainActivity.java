package com.example.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

public class MainActivity extends Activity {

	HistoryFileManager HistFile;
	
	Button btn_newApp;
	Button btn_clearApp;
	Button btn_HistApp;
	Button btn_saveApp;
	Button btn_createApp;
	
	EditText edit_AppName;
	EditText edit_AppDescr;
	
	RadioButton rb_ApiLocal;
	RadioButton rb_Api18;
	RadioButton rb_Api19;
	RadioButton rb_Api20;
	
	CheckBox cb_Calc;
	CheckBox cb_Cal;
	CheckBox cb_Msg;
	CheckBox cb_Rep;
	CheckBox cb_GeoLoc;
	CheckBox cb_Appel;
	CheckBox cb_Credit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// associate the button from interface to code
		initInterface();
		addButtonClickListner();
		
		//Check history file if it exist
		HistFile = new HistoryFileManager();
		if (!HistFile.checkHistoryFile())
			btn_HistApp.setEnabled(false);
	}

	private void addButtonClickListner() {
		
		btn_newApp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			}
		});
		
		btn_clearApp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			}
		});
		
		btn_HistApp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			}
		});
		
		btn_createApp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			}
		});
		
		btn_saveApp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!HistFile.checkHistoryFile()){
					HistFile.createEmptyRacine();;
				}
				else{
					HistFile.createEmptyRacine();;
				}					
			}
		});
	}

	private void initInterface() {
		/*Ici vousdevez faire toute les associations entre l'interface 
		 * et le code cource
		 * */
		btn_newApp = (Button) findViewById(R.id.btnNewApplication);
		btn_clearApp = (Button) findViewById(R.id.btnClearFields);
		btn_HistApp = (Button) findViewById(R.id.btnShowHistory);
		btn_saveApp = (Button) findViewById(R.id.btnSaveField);
		btn_createApp = (Button) findViewById(R.id.btnCreatApplication);
		
		edit_AppName = (EditText) findViewById(R.id.editTextAppName);
		edit_AppDescr = (EditText) findViewById(R.id.editTextAppDescr);
		
		rb_ApiLocal = (RadioButton) findViewById(R.id.radioApiLocal);
		rb_Api18 = (RadioButton) findViewById(R.id.radioApi18);
		rb_Api19 = (RadioButton) findViewById(R.id.radioApi19);
		rb_Api20 = (RadioButton) findViewById(R.id.radioApi20);
		
		cb_Calc = (CheckBox) findViewById(R.id.checkBoxCalc);
		cb_Cal = (CheckBox) findViewById(R.id.checkBoxCal);
		cb_Msg = (CheckBox) findViewById(R.id.checkBoxMsg);
		cb_Rep = (CheckBox) findViewById(R.id.checkBoxRep);
		cb_GeoLoc = (CheckBox) findViewById(R.id.checkBoxGeoLoc);
		cb_Appel = (CheckBox) findViewById(R.id.checkBoxAppel);
		cb_Credit = (CheckBox) findViewById(R.id.checkBoxCredi);
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
}
