package de.fhkoeln.cvogt.android.intents;

// Programmierung verteilter und mobiler Anwendungen
// Prof. Dr. Carsten Vogt
// TH Köln, Institut für Nachrichtentechnik
// Stand: 1.2.2016

// Um die Audio-Funktion testen zu können, muss die Konstante audioFilePath zu einer vorhandenen Audio-Datei führen

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.*;
import android.util.Log;
import android.view.*;
import android.net.Uri;
import android.content.*;
import java.io.*;

public class MainActivity extends Activity {

	private EditText eingabefeld;
	private Button powerusagebutton, dialbutton, sendbutton, audiobutton, anotheractivitybutton;
	// final String audioFilePath = "/storage/self/primary/Music/CanCan.mp3";  // Pfad zu einer Audiodatei
	final String audioFilePath = "/sdcard/Music/test.mp3";  // Pfad zu einer Audiodatei

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainactivity);
        setTitle(R.string.main_activity_title);

		// Buttons, über die verschiedene Intents abgesendet werden (siehe unten: onClick())

		powerusagebutton = (Button) findViewById(R.id.Powerusagebutton);
		eingabefeld = (EditText) findViewById(R.id.Eingabefeld);
		dialbutton = (Button) findViewById(R.id.Dialbutton);
		sendbutton = (Button) findViewById(R.id.Sendbutton);
		sendbutton = (Button) findViewById(R.id.Sendbutton);
		audiobutton = (Button) findViewById(R.id.Audiobutton);
		anotheractivitybutton = (Button) findViewById(R.id.Anotheractivitybutton);

	}
	
	// In onClick() werden, je nach gedrücktem Button, unterschiedliche Intents abgesendet

	public void onClick(View v) {
		
		// Anzeige von Informationen über den Energieverbrauch
		
		if (v==powerusagebutton) {
			Intent intent = new Intent(Intent.ACTION_POWER_USAGE_SUMMARY);
			startActivity(intent);
		}
		
		// Öffnen des Dialers mit einer Telefonnummer, die über das EditText-Feld eingegeben wurde
		
		if (v==dialbutton) {
			String uriString = "tel:"+eingabefeld.getText().toString();
			Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse(uriString));
			startActivity(intent);
		}
		
		// Öffnen eines Mailprogramms, wobei Empfängeradressen, Subject und Inhalt der Mail schon voreingestellt sind
		
		if (v==sendbutton) {
			Intent intent = new Intent(Intent.ACTION_SEND); 
			intent.setType("plain/text");
			String empfaenger[] = { "asterix@gallier.fr","obelix@gallier.fr" };  
			String empfaengerCC[] = { "idefix@gallier.fr" };  
			String empfaengerBCC[] = { "miraculix@gallier.fr" };  
			intent.putExtra(android.content.Intent.EXTRA_EMAIL, empfaenger);  
			intent.putExtra(android.content.Intent.EXTRA_CC, empfaengerCC);  
			intent.putExtra(android.content.Intent.EXTRA_BCC, empfaengerBCC);  
			intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Betrifft: Testmail");  
			intent.putExtra(android.content.Intent.EXTRA_TEXT, "Dies ist eine Testmail.");  
    		Log.v("DEMO",">>> Action: "+intent.getAction());
			Log.v("DEMO",">>> Extra - EXTRA_TEXT: "+intent.getExtras().getString(Intent.EXTRA_TEXT));
			startActivity(intent);
		}
		
		// Öffnen der Applikation zum Abspielen einer bestimmten Medien-Datei

		if (v==audiobutton) {
		 Intent intent = new Intent(Intent.ACTION_VIEW);
         File file = new File(audioFilePath);
		 intent.setDataAndType(Uri.fromFile(file), "audio/*");
		 Log.v("DEMO",">>> Action: "+intent.getAction());
		 Log.v("DEMO",">>> Data: "+intent.getData());
		 startActivity(intent);
		}
		
		// Absenden eines impliziten Intents, der von der Activity IntentsAndroid2 entgegengenommen wird (siehe Manifest-Datei)
		
		if (v==anotheractivitybutton) {
			Intent intent = new Intent("de.fhkoeln.cvogt.android.intents.WER_IMMER_DAS_WILL");
            intent.putExtra("Message","This is a message");
			startActivity(intent);
		}

	}

}

/* Alt: Mit Intent-Zugriff auf die Contacts-Datenbank, was nunmehr "deprecated" ist 


import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.net.Uri;
import android.content.*;
import android.provider.Contacts;
import android.util.Log;

public class IntentsAndroid extends Activity {

	private EditText eingabefeld;
	private Button dialbutton, viewpeoplebutton, editpeoplebutton, nextactivitybutton;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ContentValues valuesToWrite = new ContentValues();
		valuesToWrite.put(Contacts.People.NAME, "Donald Duck");
		ContentResolver myContentResolver = getContentResolver();
		Uri uri=myContentResolver.insert(Contacts.People.CONTENT_URI, valuesToWrite);
		Toast.makeText(this,"new entry: "+uri.getPath(),Toast.LENGTH_LONG).show();
		Log.v("DEMO",">>> new entry: "+uri.getPath());

		eingabefeld = (EditText) findViewById(R.id.Eingabefeld);

		dialbutton = (Button) findViewById(R.id.Dialbutton);
		viewpeoplebutton = (Button) findViewById(R.id.Viewpeoplebutton);
		editpeoplebutton = (Button) findViewById(R.id.Editpeoplebutton);
		nextactivitybutton = (Button) findViewById(R.id.Nextactivitybutton);

		dialbutton.setOnClickListener(new ButtonListener());
		viewpeoplebutton.setOnClickListener(new ButtonListener());
		editpeoplebutton.setOnClickListener(new ButtonListener());
		nextactivitybutton.setOnClickListener(new ButtonListener());

	}

	class ButtonListener implements OnClickListener  {

		public void onClick(View v) {
			if (v==dialbutton) {
				String uriString = "tel:"+eingabefeld.getText().toString();
				Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse(uriString));
				startActivity(intent);
			} 
			if (v==viewpeoplebutton) {
				String uriString ="content://contacts/people/"+eingabefeld.getText().toString();
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uriString));
				startActivity(intent);
			}
			if (v==editpeoplebutton) {
				String uriString = "content://contacts/people/"+eingabefeld.getText().toString();
				Intent intent = new Intent(Intent.ACTION_EDIT, Uri.parse(uriString));
				startActivity(intent);
			}
			if (v==nextactivitybutton) {
				// Impliziter Intent
				Intent intent = new Intent("android.intents.WER_IMMER_DAS_WILL");
				startActivity(intent);
			}

		}

	}

}

*/