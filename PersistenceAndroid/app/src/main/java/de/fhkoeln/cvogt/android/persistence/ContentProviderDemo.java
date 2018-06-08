package de.fhkoeln.cvogt.android.persistence;

// Programmierung verteilter und mobiler Anwendungen
// Prof. Dr. Carsten Vogt
// TH Köln, Institut für Nachrichtentechnik
// Stand: 5.5.2017

import android.app.Activity;
import android.os.Bundle;
import android.content.*;
import android.database.*;
import android.provider.MediaStore;
import android.view.*;
import android.widget.*;
import android.util.*;
import android.graphics.*;
import android.provider.MediaStore.*;
import android.net.Uri;
import java.io.*;

public class ContentProviderDemo extends Activity {

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.contentprovider);

		String meldung;  // Hilfsvariable zur Ausgabe von Meldungen
		EditText ausgabefeld = (EditText) findViewById(R.id.ausgabefeld);  // Feld zur Ausgabe von Informationen

		// Erzeugung eines Objekts der Klasse 'ContentValues'.
		// Hier können Datensätze abgelegt werden, die ein ContentResolver-Objekt anschließend verarbeiten kann.

		ContentValues valuesToWrite = new ContentValues();

		// Ablage von Paaren aus Schlüsseln und Werten in dem ContentValues-Objekt.

		valuesToWrite.put(Images.Media.TITLE,"Android Logo - HQ");
		valuesToWrite.put(Images.Media.DESCRIPTION,"Android Logo - Maximale Groesse");
		valuesToWrite.put(Images.Media.MIME_TYPE,"image/jpeg");

		// Erzeugung eines ContentResolver-Objekts, über das man anschließend auf Tabellen zugreifen kann.

		ContentResolver myContentResolver = getContentResolver();

		// Übertragung des Datensatzes aus dem ContentValues-Objekt in die vordefinierte Tabelle zur Verwaltung von Bildern.
		// Rückgabewert: URI des neu geschriebenen Datensatzes.

		Uri uriNeueintrag = myContentResolver.insert(Images.Media.EXTERNAL_CONTENT_URI, valuesToWrite);

		// Speicherung des eigentlichen Bilds

		try {
			OutputStream out = myContentResolver.openOutputStream(uriNeueintrag);  // Dies funktioniert für neuere Android-Versionen nicht, da dort der Zugriff auf den externen Speicher eingeschränkt ist (siehe http://stackoverflow.com/questions/17913525/saving-a-bitmap-to-disk-gallery-in-android-4-3 und )
			Bitmap myBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);  // Zugriff auf PNG-Datei mit Bilddaten
			myBitmap.compress(Bitmap.CompressFormat.JPEG,100,out);  // JPEG mit maximaler Größe erzeugen
			out.flush();
			out.close();
		} catch (Exception e) {
			meldung = e.getMessage();
			Toast.makeText(this,meldung,Toast.LENGTH_LONG).show();
			Log.v("DEMO",meldung);
		}

		// Kontrollausgabe: URI des neuen Eintrags
		
		meldung = uriNeueintrag.getScheme() + "://" + uriNeueintrag.getAuthority() + uriNeueintrag.getPath();
		ausgabefeld.setText(meldung);

		// Vorgang wiederholen mit zwei weiteren Bildern

		valuesToWrite.put(Images.Media.TITLE,"Smiley 1");
		valuesToWrite.put(Images.Media.DESCRIPTION,"Smiley traurig");
		valuesToWrite.put(Images.Media.MIME_TYPE,"image/png");
		uriNeueintrag = myContentResolver.insert(Images.Media.EXTERNAL_CONTENT_URI, valuesToWrite);
		try {
			OutputStream out = myContentResolver.openOutputStream(uriNeueintrag);  // Dies funktioniert für neuere Android-Versionen nicht, da dort der Zugriff auf den externen Speicher eingeschränkt ist (siehe http://stackoverflow.com/questions/17913525/saving-a-bitmap-to-disk-gallery-in-android-4-3 und )
			Bitmap myBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.smiley1);  // Zugriff auf PNG-Datei mit Bilddaten
			myBitmap.compress(Bitmap.CompressFormat.JPEG,100,out);  // JPEG mit maximaler Größe erzeugen
			out.flush();
			out.close();
		} catch (Exception e) {
			meldung = e.getMessage();
			Toast.makeText(this,meldung,Toast.LENGTH_LONG).show();
			Log.v("DEMO",meldung);
		}
		meldung = uriNeueintrag.getScheme() + "://" + uriNeueintrag.getAuthority() + uriNeueintrag.getPath();
		ausgabefeld.setText(ausgabefeld.getText()+"\n"+meldung);

		valuesToWrite.put(Images.Media.TITLE,"Smiley 2");
		valuesToWrite.put(Images.Media.DESCRIPTION,"Smiley fröhlich");
		valuesToWrite.put(Images.Media.MIME_TYPE,"image/png");
		uriNeueintrag = myContentResolver.insert(Images.Media.EXTERNAL_CONTENT_URI, valuesToWrite);
		try {
			OutputStream out = myContentResolver.openOutputStream(uriNeueintrag);  // Dies funktioniert für neuere Android-Versionen nicht, da dort der Zugriff auf den externen Speicher eingeschränkt ist (siehe http://stackoverflow.com/questions/17913525/saving-a-bitmap-to-disk-gallery-in-android-4-3 und )
			Bitmap myBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.smiley2);  // Zugriff auf PNG-Datei mit Bilddaten
			myBitmap.compress(Bitmap.CompressFormat.JPEG,100,out);  // JPEG mit maximaler Größe erzeugen
			out.flush();
			out.close();
		} catch (Exception e) {
			meldung = e.getMessage();
			Toast.makeText(this,meldung,Toast.LENGTH_LONG).show();
			Log.v("DEMO",meldung);
		}
		meldung = uriNeueintrag.getScheme() + "://" + uriNeueintrag.getAuthority() + uriNeueintrag.getPath();
		ausgabefeld.setText(ausgabefeld.getText()+"\n"+meldung);


		/*
		// Vorgang wiederholen mit Kompression zu JPEG-Bildern mit mittlerer und geringer Größe

		valuesToWrite.put(Images.Media.TITLE,"Android Logo - MQ");
		valuesToWrite.put(Images.Media.DESCRIPTION,"Android Logo - Mittlere Groesse");

		uriNeueintrag = myContentResolver.insert(Images.Media.EXTERNAL_CONTENT_URI, valuesToWrite);

		try {
			OutputStream out = myContentResolver.openOutputStream(uriNeueintrag);  // Dies funktioniert für neuere Android-Versionen anscheinend nicht
			Bitmap myBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
			myBitmap.compress(Bitmap.CompressFormat.JPEG,50,out);
			out.flush();
			out.close();
		} catch (Exception e) {
			meldung = e.getMessage();
			Toast.makeText(this,meldung,Toast.LENGTH_LONG).show();
			Log.v("DEMO",meldung);
		}

		meldung = ausgabefeld.getText().toString() + "\n\n";
		meldung += uriNeueintrag.getScheme() + "://" + uriNeueintrag.getAuthority() + uriNeueintrag.getPath();
		ausgabefeld.setText(meldung);

		valuesToWrite.put(Images.Media.TITLE,"Android Logo - LQ");
		valuesToWrite.put(Images.Media.DESCRIPTION,"Android Logo - Minimale Groesse");

		uriNeueintrag = myContentResolver.insert(Images.Media.EXTERNAL_CONTENT_URI, valuesToWrite);

		try {
			OutputStream out = myContentResolver.openOutputStream(uriNeueintrag);  // Dies funktioniert für neuere Android-Versionen anscheinend nicht
			Bitmap myBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
			myBitmap.compress(Bitmap.CompressFormat.JPEG,0,out);
			out.flush();
			out.close();
		} catch (Exception e) {
			meldung = e.getMessage();
			Toast.makeText(this,meldung,Toast.LENGTH_LONG).show();
			Log.v("DEMO",meldung);
		}

		meldung = ausgabefeld.getText().toString() + "\n\n";
		meldung += uriNeueintrag.getScheme() + "://" + uriNeueintrag.getAuthority() + uriNeueintrag.getPath();
		ausgabefeld.setText(meldung);
*/

	}

	// Listener-Methode für den Button zur Info-Anzeige (siehe res/layout/main.xml):
	// Zeigt Informationen über die Einträge der Bildergalerie an.

	public void zeigeInfo(View v) {
		EditText ausgabefeld = (EditText) findViewById(R.id.ausgabefeld);
		String ausgabetext;
		// Ermittlung und Ausgabe der Anzahl, der Titel und der Beschreibungen der Bilder mittels einer SQL-Query
		// Die früher gebräuchliche Activity-Methode managedQuery() ist deprecated:
		//      Cursor cur = managedQuery(Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
		// Statt dessen sollte, wie hier, die Methode query() des ContentResolvers der aktuellen Activity verwendet werden:
		Cursor cur = this.getContentResolver().query(Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
		ausgabetext = "Insgesamt "+cur.getCount()+" Bilder:\n";
		int idColumn = cur.getColumnIndex(Images.Media._ID);
		int titleColumn = cur.getColumnIndex(Images.Media.TITLE);
		int descColumn = cur.getColumnIndex(Images.Media.DESCRIPTION);
		int mimetypeColumn = cur.getColumnIndex(Images.Media.MIME_TYPE);
		if (cur.moveToFirst()) {
			do {
				ausgabetext += cur.getString(idColumn)+".) " + cur.getString(titleColumn)+"\n";
				ausgabetext += "   " + cur.getString(descColumn)+"\n";
				ausgabetext += "   " + cur.getString(mimetypeColumn)+"\n";
			} while (cur.moveToNext());
		}
		ausgabefeld.setText(ausgabetext);
	}

    // Methode zum Einlesen von Thumbnails
    // (nur zur Information, wie man das macht; die Methode wird in dieser Beispielapplikation nicht aufgerufen)

    void liesThumbnails() {

        Cursor cursor = this.getContentResolver().query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        do {
            int imageID = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Thumbnails._ID));
            Uri uri = Uri.withAppendedPath(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, Integer.toString(imageID));
            try {
                Bitmap image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                // ...
                // Die Variable image referenziert jetzt das eingelesene Thumbnail, das dann angezeigt oder weiterverarbeitet werden kann
                // ...
            } catch (Exception e) { }
        } while (cursor.moveToNext());

    }

}



/* Altes Programm mit den "deprecated" Contacts-Klassen (statt ContextContract)

import android.app.Activity;
import android.os.Bundle;
import android.net.Uri;
import android.content.*;
import android.provider.Contacts;
import android.provider.Contacts.People.ContactMethods;
import android.provider.Contacts.People.Phones;
import android.database.*;
import android.widget.*;
import android.util.*;

// nach Mosemann / Kose

// im Manifest erforderlich:
// <uses-permission android:name="android.permission.WRITE_CONTACTS" />
// <uses-permission android:name="android.permission.READ_CONTACTS" />


public class ContentProviderAndroid extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.main);
    }
    
    public void onStart() {
        try {
        	super.onStart();
            Log.v("DEMO","---> onStart() beginnt <--- ");

        	// Schreiben von Eintr�gen in die People-Tabelle und deren Unterverzeichnisse
        	
            ContentValues valuesToWrite = new ContentValues();
              // In einem Objekt der Klasse 'ContentValues' k�nnen Datens�tze abgelegt werden,
              // die ein ContentResolver-Objekt anschlie�end verarbeiten kann.

            valuesToWrite.put(Contacts.People.NAME, "Donald Duck");
              // Legt ein Paar aus Schl�ssel und Wert in dem ContentValues-Objekt ab.

            ContentResolver myContentResolver = getContentResolver();
              // Liefert ein ContentResolver-Objekt, �ber das anschlie�end auf Tabellen zugegriffen werden kann.
            
            Uri uriNeueintrag = myContentResolver.insert(Contacts.People.CONTENT_URI, valuesToWrite);
              // �bertr�gt den Datensatz aus dem ContentValues-Objekt in die vordefinierte Contacts.People-Tabelle.
              // R�ckgabewert: URI des neu geschriebenen Datensatzes.
            
            String geschrieben = (String) (valuesToWrite.get(Contacts.People.NAME));
            Toast.makeText(this,"Name "+geschrieben+" geschrieben",Toast.LENGTH_LONG).show();
            Log.v("DEMO","---> Name "+geschrieben+" geschrieben <--- ");
            
            Uri phoneUri = Uri.withAppendedPath(uriNeueintrag,Contacts.People.Phones.CONTENT_DIRECTORY);
              // Ermittelt die URI der Untertabelle von Contacts.People, in der Telefondaten gespeichert werden.

            valuesToWrite.clear();
            valuesToWrite.put(Contacts.Phones.TYPE, Phones.TYPE_MOBILE);
            valuesToWrite.put(Contacts.Phones.NUMBER, "123456");
            getContentResolver().insert(phoneUri, valuesToWrite);
              // Schreibt in diese Tabelle die Nummer eines Mobiltelefons.
            
            geschrieben = (String) (valuesToWrite.get(Contacts.Phones.NUMBER));
            Toast.makeText(this,"Tel.nummer "+geschrieben+" geschrieben",Toast.LENGTH_LONG).show();
            Log.v("DEMO","---> Tel.nummer "+geschrieben+" geschrieben <--- ");
            
            Uri emailUri = Uri.withAppendedPath(uriNeueintrag,ContactMethods.CONTENT_DIRECTORY);
              // Ermittelt die URI der Tabelle, in der Kontaktdaten außer Telefonnummern gespeichert werden.

            valuesToWrite.clear();
            valuesToWrite.put(ContactMethods.KIND, Contacts.KIND_EMAIL);
            valuesToWrite.put(ContactMethods.DATA, "donald.duck@mickeymouse.com");
            valuesToWrite.put(ContactMethods.TYPE, ContactMethods.TYPE_HOME);
            getContentResolver().insert(emailUri, valuesToWrite);
              // Schreibt in diese Tabelle eine Mailadresse.
            
            geschrieben = (String) (valuesToWrite.get(ContactMethods.DATA));
            Toast.makeText(this,"E-Mail "+geschrieben+" geschrieben",Toast.LENGTH_LONG).show();
            Log.v("DEMO","---> E-Mail "+geschrieben+" geschrieben <--- ");
            
        	// Lesen von Einträgen aus der People-Tabelle und deren Unterverzeichnissen
            
            // String[] projection = new String[] {
            //        Contacts.People.NAME
            //     };
            // Cursor cur = managedQuery(Contacts.People.CONTENT_URI, projection, null, null, null);
  
            Cursor cur = managedQuery(Contacts.People.CONTENT_URI, null, null, null, null);
              // Erzeugung eines Cursors, mit dem die Ergebnistabelle eines Datenbankzugriffs durchlaufen werden kann.
              // Hier: Der gesamte Inhalt der Contacts.People-Tabelle.
            
            int nameColumn = cur.getColumnIndex(Contacts.People.NAME);
            cur.moveToFirst();
            String name = cur.getString(nameColumn);
              // Ermittelt den Eintrag in der ersten Zeile / NAME-Spalte der Ergebnistabelle
            
            Toast.makeText(this,"Gelesener Name: "+name,Toast.LENGTH_LONG).show();
            Log.v("DEMO","---> Gelesen: "+name+" <--- ");
            
          } catch (Exception e) { }
          }
}

*/