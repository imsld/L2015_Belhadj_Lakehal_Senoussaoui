package com.pfe.bls;

import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class HistoriqueActivity extends Activity {

	private HistoryFileManager HistFile;
	Intent myintent;
	HashMap<String, String> Info;

	Button btn_Retour;
	Button btn_Actualiser;
	Button btn_Supprimer;
	TextView textView;
	ListView listView1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historique);

		// associate the button from interface to code
		initInterface();
		addButtonClickListner();
		HistFile = (HistoryFileManager) getIntent().getParcelableExtra(
				MainActivity.PAR_KEY);
		// Adding menuItems to ListView
		// android.R.layout.activity_list_item;
		ListAdapter adapter = new SimpleAdapter(this, HistFile.menuItems,
				R.layout.list_application, new String[] {
						HistFile.KEY_NOM_APPLICATION,
						HistFile.KEY_DATE_CREATION },
				new int[] { R.id.textViewNameApplication,
						R.id.textViewDateCreation });

		listView1.setAdapter(adapter);
		
		listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parentAdapter, View view,
					int position, long id) {

				Info = (HashMap<String, String>) listView1
						.getItemAtPosition(position);

				String msg = "Nom Application : "
						+ Info.get(HistFile.KEY_NOM_APPLICATION) + "\n"
						+ "Date création : "
						+ Info.get(HistFile.KEY_DATE_CREATION) + "\n"
						+ "Version SDK :" + Info.get(HistFile.KEY_VERSION_SDK)
						+ "\n" + "Description :"
						+ Info.get(HistFile.KEY_DESCRIPTION) + "\n"
						+ "Services :";
				if (Info.get(HistFile.KEY_CALCULATRICE).compareTo("0") != 0)
					msg = msg + "\n  - " + Info.get(HistFile.KEY_CALCULATRICE);

				if (Info.get(HistFile.KEY_CALENDRIER).compareTo("0") != 0)
					msg = msg + "\n  - " + Info.get(HistFile.KEY_CALENDRIER);

				if (Info.get(HistFile.KEY_MESSAGERIE).compareTo("0") != 0)
					msg = msg + "\n  - " + Info.get(HistFile.KEY_MESSAGERIE);

				if (Info.get(HistFile.KEY_REPARTOIRE).compareTo("0") != 0)
					msg = msg + "\n  - " + Info.get(HistFile.KEY_REPARTOIRE);

				if (Info.get(HistFile.KEY_Mms).compareTo("0") != 0)
					msg = msg + "\n  - " + Info.get(HistFile.KEY_Mms);

				if (Info.get(HistFile.KEY_APPEL).compareTo("0") != 0)
					msg = msg + "\n  - " + Info.get(HistFile.KEY_APPEL);

				if (Info.get(HistFile.KEY_Galerie).compareTo("0") != 0)
					msg = msg + "\n  - " + Info.get(HistFile.KEY_Galerie);

				AlertDialog.Builder builder = new AlertDialog.Builder(
						HistoriqueActivity.this);

				DialogInterface.OnClickListener listenerYes = new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						int code = Integer.parseInt(Info.get(HistFile.KEY_ID));
						setResult(code);
						HistoriqueActivity.this.finish();
					}
				};

				DialogInterface.OnClickListener listenerNo = new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				};

				builder.setMessage(msg)
						.setPositiveButton(R.string.SelectYes, listenerYes)
						.setNegativeButton(R.string.SelectNo, listenerNo);

				AlertDialog d = builder.create();
				d.setTitle("Informations");
				d.show();

			}

		});
		
		
		//récupération du bouton delete d'un item
//		Button btn_DeleteItem = (Button)findViewById(R.id.buttonDeleteItem);
//		
//		btn_DeleteItem.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//		
//		btn_DeleteItem.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				AlertDialog.Builder builder1 = new AlertDialog.Builder(v
//						.getContext());
//
//				DialogInterface.OnClickListener listenerYes1 = new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int id) {
//						//Traitement à faire
//						Toast.makeText(getApplicationContext(),
//								"Suppression réussie", Toast.LENGTH_SHORT)
//								.show();
//					}
//				};
//
//				DialogInterface.OnClickListener listenerNo1 = new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int id) {
//						// User cancelled the dialog
//					}
//				};
//
//				builder1.setMessage(R.string.supprimerApplication)
//						.setPositiveButton(R.string.yes, listenerYes1)
//						.setNegativeButton(R.string.no, listenerNo1);
//
//				AlertDialog d1 = builder1.create();
//				d1.setTitle("Supprimer application");
//				d1.show();
//
//			}
//		});
//
	}

	private void addButtonClickListner() {
		// TODO Auto-generated method stub
		btn_Retour.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setResult(0);
				HistoriqueActivity.this.finish();
			}
		});

		btn_Actualiser.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			}
		});

		btn_Supprimer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(v
						.getContext());

				DialogInterface.OnClickListener listenerYes = new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						HistFile.deleteCurentApplication();
						Toast.makeText(getApplicationContext(),
								"Suppression réussie", Toast.LENGTH_SHORT)
								.show();
					}
				};

				DialogInterface.OnClickListener listenerNo = new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// User cancelled the dialog
					}
				};

				builder.setMessage(R.string.supprimerAllApplications)
						.setPositiveButton(R.string.yes, listenerYes)
						.setNegativeButton(R.string.no, listenerNo);

				AlertDialog d = builder.create();
				d.setTitle("Supprimer historique");
				d.show();
			}
		});

	}

	private void initInterface() {
		/*
		 * Ici vousdevez faire toute les associations entre l'interface et le
		 * code cource
		 */
		btn_Retour = (Button) findViewById(R.id.btnRetour);
		btn_Actualiser = (Button) findViewById(R.id.bntActualiser);
		btn_Supprimer = (Button) findViewById(R.id.bntSupprimer);
		textView = (TextView) findViewById(R.id.textView);
		listView1 = (ListView) findViewById(R.id.listView1);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.historique, menu);
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
