package de.fhkoeln.cvogt.android.persistence;

// Programmierung verteilter und mobiler Anwendungen
// Prof. Dr. Carsten Vogt
// TH Köln, Institut für Nachrichtentechnik
// Stand: 11.12.2015

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class MainActivity extends ListActivity {
      
String[] choices = { "Dateisystem", "SQLite", "Content Provider", "Shared Preferences",
        // "PreferenceActivity",
        "Settings (Default Shared Prefs.)" };

ListAdapter adapter;

public void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,choices);
 setListAdapter(adapter);
 setTitle(R.string.activity_main_title);
}

protected void onListItemClick(ListView liste, View datenElement, int position, long id) {
     super.onListItemClick(liste, datenElement, position, id);
     String auswahl = ((TextView)datenElement).getText().toString();
     if (auswahl.equals("Dateisystem")) 
             startActivity (new Intent (this,FileSystemDemo.class));
     if (auswahl.equals("SQLite")) 
             startActivity (new Intent (this, SQLiteDemo.class));
     if (auswahl.equals("Content Provider")) 
         startActivity (new Intent (this, ContentProviderDemo.class));
     if (auswahl.equals("Shared Preferences")) 
         startActivity (new Intent (this, SharedPreferencesDemo.class));
     // if (auswahl.equals("PreferenceActivity"))
     //  startActivity (new Intent (this, PreferenceActivityDemo.class));
     if (auswahl.equals("Settings (Default Shared Prefs.)"))
         startActivity (new Intent (this, UserOfDefaultPreferencesDemo.class));
   }

}


