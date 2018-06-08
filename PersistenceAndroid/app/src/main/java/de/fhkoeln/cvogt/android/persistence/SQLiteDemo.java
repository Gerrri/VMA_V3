package de.fhkoeln.cvogt.android.persistence;

// Programmierung verteilter und mobiler Anwendungen
// Prof. Dr. Carsten Vogt
// TH Köln, Institut für Nachrichtentechnik
// Stand: 11.12.2015

// Diese Activity zeigt die Adhoc-Definition und Benutzung einer SQLite-Datenbank.
// Die systematische Vorgehensweise gemäß der Android-Dokumentation
// https://developer.android.com/training/basics/data-storage/databases.html
// wird im Projekt SQLiteAndroid gezeigt.

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteDemo extends Activity {
	
  public void onCreate(Bundle savedInstanceState) {
	  
    super.onCreate(savedInstanceState);
    setContentView(R.layout.sqlite);

    Button suchbutton = (Button) findViewById(R.id.suchbutton);  // Button zur Suche in der Datenbank
    suchbutton.setOnClickListener(new SuchbuttonListener());
    
    // Erstellung und Füllen der Datenbank mit Hilfe von SQL-Befehlen

    SQLiteDatabase db = null;
    try {
      db = openOrCreateDatabase("WoerterbuchDB", MODE_PRIVATE, null);
      db.execSQL("CREATE TABLE IF NOT EXISTS DeutschEnglisch (WortDeutsch TEXT,WortEnglisch TEXT);");
      db.execSQL("INSERT INTO DeutschEnglisch VALUES ('Auto', 'car');");
      db.execSQL("INSERT INTO DeutschEnglisch VALUES ('Haus', 'house');");
      db.execSQL("INSERT INTO DeutschEnglisch VALUES ('Haus', 'home');");
      db.execSQL("INSERT INTO DeutschEnglisch VALUES ('Hausfrau', 'housewife');");
      db.execSQL("INSERT INTO DeutschEnglisch VALUES ('freistehendes Haus', 'detached house');");
      db.execSQL("INSERT INTO DeutschEnglisch VALUES ('in ein Haus einbrechen', 'to burgle a house');");
    } catch (Exception e) { }
    finally {
      db.close();
    }
  }

  class SuchbuttonListener implements View.OnClickListener {
	  
    public void onClick(View v) {
    	
      // Suche in der Datenbank
    	
      String[] columns = { "WortDeutsch", "WortEnglisch" };  // Spaltenname der Datenbank-Tabelle (benutzt für Spaltenprojektion bei query=SELECT)
      SQLiteDatabase db = null;  // Referenz auf die Datenbank
      
      try {
    	  
        db = openOrCreateDatabase("WoerterbuchDB", MODE_PRIVATE, null);  // Öffnen der Datenbank
        
        TextView sucheingabefeld = (TextView) findViewById(R.id.sucheingabe);
        String suchwort = sucheingabefeld.getText().toString();  // gesuchtes Wort gemäß Benutzereingabe
        
        Cursor ergCursor;  // Cursor zum Durchlaufen des Ergebnisses
        
        if (!suchwort.contains("%"))  // Abfrage mit Wildcard?
          ergCursor = db.query(true, "DeutschEnglisch", columns, "WortDeutsch = '"+suchwort+"'", null, null, null, null, null);
        else 
          ergCursor = db.query(true, "DeutschEnglisch", columns, "WortDeutsch like '"+suchwort+"'", null, null, null, null, null); // Abfrage mit Wildcard
        
        if (ergCursor != null) {
          // Durchlaufen des Ergebnis-Cursors und Zusammenstellen der Ausgabe
          int wortdeutschIndex = ergCursor.getColumnIndexOrThrow("WortDeutsch");
          int wortenglischIndex = ergCursor.getColumnIndexOrThrow("WortEnglisch");
          String result = "";
          if (ergCursor.moveToFirst()) {
            do {
              String wortdeutschStr = ergCursor.getString(wortdeutschIndex);
              String wortenglischStr = ergCursor.getString(wortenglischIndex);
              result = result + wortdeutschStr + " : " + wortenglischStr + "\n";
            } while (ergCursor.moveToNext());
          }
          
          // Ausgabe des Ergebnisses
          TextView suchergebnisfeld = (TextView) findViewById(R.id.suchergebnis);
          suchergebnisfeld.setText(result); 
          
        }
      } catch (Exception e) {
      } finally {
        db.close();
      }
      
    }
    
  }
  
}