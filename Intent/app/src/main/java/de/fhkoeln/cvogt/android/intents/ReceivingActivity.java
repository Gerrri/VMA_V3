package de.fhkoeln.cvogt.android.intents;

// Programmierung verteilter und mobiler Anwendungen
// Prof. Dr. Carsten Vogt
// TH Köln, Institut für Nachrichtentechnik
// Stand: 11.12.2015

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.*;
import java.util.*;

public class ReceivingActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.receivingactivity);
		
		// Abfrage und Anzeige von Informationen über den Intent, der die Activity gestartet hat
		Intent intent = getIntent();
		String action = intent.getAction();
		Set<String> categories = intent.getCategories();

		Log.v("DEMO",">>> Activity2: Infos ueber Intent <<<");
		Log.v("DEMO",">>> Action: "+action);
		if (categories==null||categories.size()==0)
			Log.v("DEMO",">>> No categories");
		else {
			Log.v("DEMO",">>> Categories:");
			for (Iterator it=categories.iterator();it.hasNext();)
				Log.v("DEMO",">>> "+(String)it.next());
		}
		Log.v("DEMO",">>> Extras: "+intent.getStringExtra("Message"));

	}

}