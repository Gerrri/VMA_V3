package de.fhkoeln.cvogt.android.persistence;

// Programmierung verteilter und mobiler Anwendungen
// Prof. Dr. Carsten Vogt
// TH Köln, Institut für Nachrichtentechnik
// Stand: 11.02.2016

import android.app.Activity;
import android.os.Bundle;
import android.content.*;
import android.database.*;
import android.view.*;
import android.widget.*;
import android.util.*;
import android.graphics.*;
import android.provider.MediaStore.*;
import android.net.Uri;
import java.io.*;

public class SharedPreferencesDemo extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.sharedpreferences);

		// Beim Erzeugen der Activity:
		// Laden eines Texts aus einer benannten Shared-Preferences-Datei "MySharedPreferences"
		// und Anzeige in einem EditText.
		// Der Text wurde unter dem Key "Text" abgespeichert (siehe unten: onStop()).
		// Ist kein solcher Key vorhanden, so wird als Default-Wert der zweite Parameter von
		// sp.getString(...) benutzt.

		SharedPreferences sp = getSharedPreferences("MySharedPreferences",0);
		String eingelesenerText = sp.getString("Text","--- z.Zt. nichts gespeichert; bitte Text eingeben ---");
        EditText anzeigefeld = (EditText) findViewById(R.id.anzeigefeld);
		anzeigefeld.setText(eingelesenerText);
	}

	public void onStop() {

		super.onStop();

		// Beim Stoppen der Activity:
		// Speichern eines Texts, der in einem EditText steht, in einer benannten
		// Shared-Preferences-Datei "MySharedPreferences" unter dem Key "Text".

		SharedPreferences sp = getSharedPreferences("MySharedPreferences",0);
        EditText anzeigefeld = (EditText) findViewById(R.id.anzeigefeld);
		SharedPreferences.Editor editor = sp.edit();
		String zuSpeichernderText = anzeigefeld.getText().toString();
		editor.putString("Text",zuSpeichernderText);
		editor.commit();
		
	}
	
}
