package com.pfe.bls;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.pfe.bls.R;
import com.pfe.bls.HistoriqueActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

	public final static String PAR_KEY = "com.pfe.bls.par";

	HistoryFileManager HistFile = new HistoryFileManager();

	Button btn_newApp;
	Button btn_clearApp;
	Button btn_HistApp;
	Button btn_saveApp;
	Button btn_createApp;

	EditText edit_AppName;
	EditText edit_AppDescr;

	RadioGroup rb_Group_API;
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

	String myVersion;
	int sdkVersion;
	
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
				cb_GeoLoc.setChecked(false);
				cb_Appel.setChecked(false);
				cb_Credit.setChecked(false);
			}
		});

		btn_clearApp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Getting device OS Version
				rb_ApiLocal.setText(getAPIVersion());
				rb_ApiLocal.setActivated(true);
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
				startActivity(myintent);
				Log.i("ffffffff","aaaaaaaaaaaa");
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
				List<String> listInfo = new ArrayList<String>();
				String dateCreation, heureCreation;
				dateCreation = new SimpleDateFormat("dd/MM/yyyy")
				.format(System.currentTimeMillis());
				heureCreation =  new SimpleDateFormat("HH:mm:ss")
						.format(System.currentTimeMillis());

				listInfo.add(dateCreation+","+ heureCreation);

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
					listInfo.add("Cal");
				else
					listInfo.add("0");

				if (cb_Cal.isChecked())
					listInfo.add("Calc");
				else
					listInfo.add("0");

				if (cb_Msg.isChecked())
					listInfo.add("Msg");
				else
					listInfo.add("0");

				if (cb_Rep.isChecked())
					listInfo.add("Rep");
				else
					listInfo.add("0");

				if (cb_GeoLoc.isChecked())
					listInfo.add("GeoLoc");
				else
					listInfo.add("0");

				if (cb_Appel.isChecked())
					listInfo.add("Appel");
				else
					listInfo.add("0");

				if (cb_Credit.isChecked())
					listInfo.add("Credit");
				else
					listInfo.add("0");

				HistFile.addApplication(listInfo);

				if (HistFile.getCountApplication() == 0)
					btn_HistApp.setEnabled(false);
				else
					btn_HistApp.setEnabled(true);
			}
		});
	}

	private void initInterface() {
		btn_newApp = (Button) findViewById(R.id.btnNewApplication);
		btn_clearApp = (Button) findViewById(R.id.btnClearFields);
		btn_HistApp = (Button) findViewById(R.id.btnShowHistory);
		btn_saveApp = (Button) findViewById(R.id.btnSaveField);
		btn_createApp = (Button) findViewById(R.id.btnCreatApplication);

		edit_AppName = (EditText) findViewById(R.id.editTextAppName);
		edit_AppDescr = (EditText) findViewById(R.id.editTextAppDescr);

		rb_Group_API = (RadioGroup) findViewById(R.id.radioGroupAPI);
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

	private CharSequence getAPIVersion() {
		// Getting device OS Version
		myVersion = android.os.Build.VERSION.RELEASE; // e.g. myVersion := "1.6"
		sdkVersion = android.os.Build.VERSION.SDK_INT; // e.g. sdkVersion := 8;
		return "API " + sdkVersion + ", version " + myVersion + " (local API)";
	}

}
