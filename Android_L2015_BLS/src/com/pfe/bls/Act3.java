package com.pfe.bls;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Act3 extends MainActivity implements OnClickListener {
	
	 Button btnretoure;
	 EditText editTextEmail, editTextSubject, editTextMessage;
     Button btnSend, btnAttachment;
     String email, subject, message, attachmentFile;
     Uri URI = null;
     private static final int PICK_FROM_GALLERY = 101;
     int columnIndex;

	
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.act3);
       btnretoure = (Button) findViewById(R.id.retoure2);
      
       
       
       editTextSubject = (EditText) findViewById(R.id.editTextSubject);
       editTextMessage = (EditText) findViewById(R.id.editTextMessage);
       btnAttachment = (Button) findViewById(R.id.buttonAttachment);
       btnSend = (Button) findViewById(R.id.buttonSend);

       btnSend.setOnClickListener(this);
       btnAttachment.setOnClickListener(this);
       btnretoure.setOnClickListener(this);
       
         	}
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK) {
               /**
                * Get Path
                */
               Uri selectedImage = data.getData();
               String[] filePathColumn = { MediaStore.Images.Media.DATA };

               Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
               cursor.moveToFirst();
               columnIndex = cursor.getColumnIndex(filePathColumn[0]);
               attachmentFile = cursor.getString(columnIndex);
               Log.e("Attachment Path:", attachmentFile);
               URI = Uri.parse("file://" + attachmentFile);
               cursor.close();
        }}
    
 

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
public void onClick(View v) {
		
		if (v == btnretoure){
		
		                   Intent  myintent = new Intent(v.getContext(),MainActivity.class);
		                   startActivity(myintent);}
		else if (v == btnAttachment) {
            openGallery();

     }
		else if(v == btnSend) {
            try {
                
                subject = editTextSubject.getText().toString();
                message = editTextMessage.getText().toString();

                final Intent emailIntent = new Intent(
                              android.content.Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                              new String[] { "sofianeikramasma@outlook.fr" });
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                              subject);
                if (URI != null) {
                       emailIntent.putExtra(Intent.EXTRA_STREAM, URI);
                }
                emailIntent
                              .putExtra(android.content.Intent.EXTRA_TEXT, message);
                this.startActivity(Intent.createChooser(emailIntent,
                              "Sending email..."));

          } catch (Throwable t) {
                Toast.makeText(this,
                              "Request failed try again: " + t.toString(),
                              Toast.LENGTH_LONG).show();
          }
   }
		
	}

public void openGallery() {
    Intent intent = new Intent();
    intent.setType("image/*");
    intent.setAction(Intent.ACTION_GET_CONTENT);
    intent.putExtra("return-data", true);
    startActivityForResult(
                 Intent.createChooser(intent, "Complete action using"),
                 PICK_FROM_GALLERY);

}
}




	
	

