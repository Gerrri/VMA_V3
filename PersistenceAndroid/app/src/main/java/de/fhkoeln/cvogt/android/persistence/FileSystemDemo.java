package de.fhkoeln.cvogt.android.persistence;

// Programmierung verteilter und mobiler Anwendungen
// Prof. Dr. Carsten Vogt
// TH Köln, Institut für Nachrichtentechnik
// Stand: 8.11.2017

/* Bemerkungen zur Permission-Problematik:
Der Zugriff auf den öffentlichen Bereich des Externen Speichers ist das erste Beispiel-Programm dieses Kurses,
das Permissions benötigt (READ/WRITE_EXTERNAL_STORAGE). Schon hier muss also der Umgang mit Permissions angesprochen werden,
der in Kapitel 9 noch detaillierter besprochen wird.
In früheren Android-Versionen (vor Android 6.0, also API-Level kleiner als 23) werden alle im Manifest geforderten Permissions
gewährt, indem der Benutzer bei der Installation seine Zustimmung gibt (geschieht beim hier selbstentwickelten Code implizit).
Ab der Installation sind also entsprechende kritische Operationen (z.B. Zugriff auf den externen Speicher) generell erlaubt;
der Programmierer muss also keine Abfragen in seinen Code einbauen. Das entspricht dem hier gezeigten Beispiel-Code.
In neueren Android-Versionen (ab Android 6.0, also API-Level 23 und höher) gelten viele Permissions bei der Installation
zunächst als „denied“ (= nicht gegeben). Wenn also nichts Weiteres geschieht, stürzt das Programm bei späteren kritischen
Operationen ab. Dies lässt sich dadurch vermeiden, dass der Benutzer die Permissions über die Geräteeinstellungen
(> Apps > Appname > Berechtigungen) explizit gibt oder das Programm den Benutzer vor einer kritischen Operation um Erlaubnis
bittet (was explizit ausprogrammiert werden muss). Beides erscheint für die einfachen Beispielprogramme, die hier betrachtet
werden, nicht angemessen.
Es muss also sichergestellt werden, dass das alte Permission-Modell angewandt wird – selbst wenn das Programm auf einem Gerät
oder Emulator mit neuer Android-Version ausgeführt wird. Man erreicht dies, indem man die Target-SDK-Version kleiner als 23
wählt. Dies ist in diesem Projekt und den anderen hier gezeigten Beispiel-Projekten der Fall.
*/

import android.app.*;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.util.Log;
import android.os.Environment;
import java.io.*;

public class FileSystemDemo extends ListActivity {

  String[] choices = { "Internal Storage: Schreiben", "Internal Storage: Lesen",   // Auswahlmöglichkeiten: verschiedene Operationen auf dem Dateisystem
          "External Storage (App-eigen): Schreiben", "External Storage (App-eigen): Lesen",
          "External Storage (öffentlich): Schreiben", "External Storage (öffentlich): Lesen",
          "Info" };

  ListAdapter adapter;

  String dateinameIntern="FileIntern.txt",
          dateinameExtern="FileExtern.txt",
          dateinameExternPublic="FileExternPub.txt";

  String ausgabe;

  public void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setTitle("Operationen auf dem Dateisystem");
    adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,choices);
    setListAdapter(adapter);

  }

  protected void onListItemClick(ListView liste, View datenElement, int position, long id) {

    super.onListItemClick(liste, datenElement, position, id);

    String auswahl = ((TextView)datenElement).getText().toString();

    if (auswahl.equals("Internal Storage: Schreiben")) {

      // Schreiben in das private Hauptverzeichnis der App im internen Speicher

      Toast.makeText(getApplicationContext(), "Schreiben nach "+dateinameIntern+" (internes Haupt-Dir der App)", Toast.LENGTH_LONG).show();
      FileOutputStream fos = null;
      PrintWriter pwr = null;
      try {
        fos = this.openFileOutput(dateinameIntern,0);
        pwr = new PrintWriter(fos);
      } catch (Exception e) {
        Toast.makeText(this,"Fehler beim Öffnen: "+e.getClass(), Toast.LENGTH_LONG).show();
        Log.v("DEMO",">>>> Fehler beim Öffnen: "+e.getClass());
      } 
      try {
        pwr.println("Hallo1");
        Toast.makeText(this,"Hallo1 nach "+dateinameIntern+" geschrieben", Toast.LENGTH_LONG).show();
        Log.v("DEMO",">>>> Hallo1 nach "+dateinameIntern+" geschrieben");
      } catch (Exception e) {
        Toast.makeText(this,"Fehler beim Schreiben: "+e.getClass(), Toast.LENGTH_LONG).show();
        Log.v("DEMO",">>>> Fehler beim Schreiben: "+e.getClass());
      } 
      try {
        pwr.close();
        fos.close();
      } catch (Exception e) {
        Toast.makeText(this,"Fehler beim Schliessen: "+e.getClass(), Toast.LENGTH_LONG).show();
        Log.v("DEMO",">>>> Fehler beim Schliessen: "+e.getClass());
      }

      // Schreiben in ein neues privates Unterverzeichnis der App im internen Speicher

//      File neuesDir = this.getDir("neuesDir",Context.MODE_PRIVATE);
//      try {
//        PrintWriter pwr = new PrintWriter(neuesDir.getAbsoluteFile()+"/hallohallo.txt");
//        pwr.println("hallohallo");
//        pwr.close();
//      } catch (Exception e) {
//        Toast.makeText(this,"Fehler beim Schreiben von hallohallo: "+e.getClass(), Toast.LENGTH_LONG).show();
//        Log.v("DEMO",">>>> Fehler beim Schreiben von hallohallo: "+e.getClass());
//      } 
//      Toast.makeText(this,"Dir erzeugt: "+neuesDir.getAbsolutePath(), Toast.LENGTH_LONG).show();
//      Log.v("DEMO",">>>> Dir erzeugt: "+neuesDir.getAbsolutePath());
//      if (neuesDir.delete()) {
//        Toast.makeText(this,"... und wieder geloescht", Toast.LENGTH_LONG).show();
//        Log.v("DEMO",">>>> ... und wieder geloescht");
//      }
//      else {
//        Toast.makeText(this,"Loeschen fehlgeschlagen", Toast.LENGTH_LONG).show();
//        Log.v("DEMO",">>>> Loeschen fehlgeschlagen");
//      }

    }

    if (auswahl.equals("Internal Storage: Lesen")) {

      // Lesen aus dem privaten Hauptverzeichnis der App im internen Speicher

      Toast.makeText(getApplicationContext(), "Lesen von "+dateinameIntern+" (internes Haupt-Dir der App)", Toast.LENGTH_LONG).show();
      FileInputStream fis = null;
      BufferedReader bin = null;
      try {
        fis = this.openFileInput(dateinameIntern);
        bin = new BufferedReader(new InputStreamReader(fis));
      } catch (Exception e) {
        Toast.makeText(this,"Fehler beim Öffnen: "+e.getClass(), Toast.LENGTH_LONG).show();
        Log.v("DEMO",">>>> Fehler beim Öffnen: "+e.getClass());
      } 
      try {
        String eingabe = bin.readLine();
        Toast.makeText(this,"Gelesen: "+eingabe, Toast.LENGTH_LONG).show();
        Log.v("DEMO",">>>> Gelesen: "+eingabe);
      } catch (Exception e) {
        Toast.makeText(this,"Fehler beim Lesen: "+e.getClass(), Toast.LENGTH_LONG).show();
        Log.v("DEMO",">>>> Fehler beim Lesen: "+e.getClass());
      } 
      try {
        bin.close();
        fis.close();
      } catch (Exception e) {
        Toast.makeText(this,"Fehler beim Schließen: "+e.getClass(), Toast.LENGTH_LONG).show();
        Log.v("DEMO",">>>> Fehler beim Schließen: "+e.getClass());
      } 
    }

    if (auswahl.equals("External Storage (App-eigen): Schreiben")) {

      // Schreiben in den externen Speicher (App-spezifischer Bereich)

      // Prüfen, ob externer Speicher vorhanden und beschreibbar
      if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
        Toast.makeText(this,"Schreiben nicht möglich: "+Environment.getExternalStorageState(), Toast.LENGTH_LONG).show();
        return;
      }
      PrintWriter pwr = null;
      try {
        File extFilesDir = this.getExternalFilesDir(null);
        File outputFile = new File(extFilesDir.getAbsolutePath()+"/"+dateinameExtern);
        Toast.makeText(getApplicationContext(), "Schreiben nach "+outputFile.getPath()+" (extern/app-eigen)", Toast.LENGTH_LONG).show();
        pwr = new PrintWriter(outputFile);
      } catch (Exception e) {
        Toast.makeText(this,"Fehler beim Öffnen: "+e.getClass(), Toast.LENGTH_LONG).show();
        Log.v("DEMO",">>>> Fehler beim Öffnen: "+e.getClass());
      } 
      try {
        pwr.println("Hallo2");
        Toast.makeText(this,"Hallo2 nach "+dateinameExtern+" geschrieben", Toast.LENGTH_LONG).show();
        Log.v("DEMO",">>>> Hallo2 nach "+dateinameExtern+" geschrieben");
      } catch (Exception e) {
        Toast.makeText(this,"Fehler beim Schreiben: "+e.getClass(), Toast.LENGTH_LONG).show();
        Log.v("DEMO",">>>> Fehler beim Schreiben: "+e.getClass());
      } 
      try {
        pwr.close();
      } catch (Exception e) {
        Toast.makeText(this,"Fehler beim Schließen: "+e.getClass(), Toast.LENGTH_LONG).show();
        Log.v("DEMO",">>>> Fehler beim Schließen: "+e.getClass());
      }
      
    }

    if (auswahl.equals("External Storage (App-eigen): Lesen")) {

      // Lesen aus dem externen Speicher (App-spezifischer Bereich)

      // Prüfen, ob externer Speicher vorhanden und lesbar
      if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && !Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
        Toast.makeText(this,"Lesen nicht möglich: "+Environment.getExternalStorageState(), Toast.LENGTH_LONG).show();
        return;
      }
      
      BufferedReader bin = null;
      try {
        File extFilesDir = this.getExternalFilesDir(null);
        File inputFile = new File(extFilesDir.getAbsolutePath()+"/"+dateinameExtern);
        Toast.makeText(getApplicationContext(), "Lesen von "+inputFile.getPath()+" (extern/App-eigen)", Toast.LENGTH_LONG).show();
        bin = new BufferedReader(new FileReader(inputFile));
      } catch (Exception e) {
        Toast.makeText(this,"Fehler beim Öffnen: "+e.getClass(), Toast.LENGTH_LONG).show();
        Log.v("DEMO",">>>> Fehler beim Öffnen: "+e.getClass());
      } 
      try {
        String eingabe = bin.readLine();
        Toast.makeText(this,"Gelesen: "+eingabe, Toast.LENGTH_LONG).show();
        Log.v("DEMO",">>>> Gelesen: "+eingabe);
      } catch (Exception e) {
        Toast.makeText(this,"Fehler beim Lesen: "+e.getClass(), Toast.LENGTH_LONG).show();
        Log.v("DEMO",">>>> Fehler beim Lesen: "+e.getClass());
      } 
      try {
        bin.close();
      } catch (Exception e) {
        Toast.makeText(this,"Fehler beim Schließen: "+e.getClass(), Toast.LENGTH_LONG).show();
        Log.v("DEMO",">>>> Fehler beim Schließen: "+e.getClass());
      } 
    }

    if (auswahl.equals("External Storage (öffentlich): Schreiben")) {

      // Schreiben in den externen Speicher (öffentliches Vewrzeichnis)

      // Prüfen, ob externer Speicher vorhanden und beschreibbar
      if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
        Toast.makeText(this,"Schreiben nicht möglich: "+Environment.getExternalStorageState(), Toast.LENGTH_LONG).show();
        return;
      }
      PrintWriter pwr = null;
      try {
        File extFilesPubDir = Environment.getExternalStoragePublicDirectory("");
        File outputFile = new File(extFilesPubDir.getAbsolutePath()+"/"+dateinameExternPublic);
        Toast.makeText(getApplicationContext(), "Schreiben nach "+outputFile.getPath()+" (extern/öffentlich)", Toast.LENGTH_LONG).show();
        pwr = new PrintWriter(outputFile);
      } catch (Exception e) {
        Toast.makeText(this,"Fehler beim Öffnen: "+e.getClass(), Toast.LENGTH_LONG).show();
        Log.v("DEMO",">>>> Fehler beim Öffnen: "+e.getClass());
      }
      try {
        pwr.println("Hallo3");
        Toast.makeText(this,"Hallo3 nach "+dateinameExternPublic+" geschrieben", Toast.LENGTH_LONG).show();
        Log.v("DEMO",">>>> Hallo3 nach "+dateinameExternPublic+" geschrieben");
      } catch (Exception e) {
        Toast.makeText(this,"Fehler beim Schreiben: "+e.getClass(), Toast.LENGTH_LONG).show();
        Log.v("DEMO",">>>> Fehler beim Schreiben: "+e.getClass());
      }
      try {
        pwr.close();
      } catch (Exception e) {
        Toast.makeText(this,"Fehler beim Schließen: "+e.getClass(), Toast.LENGTH_LONG).show();
        Log.v("DEMO",">>>> Fehler beim Schließen: "+e.getClass());
      }

    }

    if (auswahl.equals("External Storage (öffentlich): Lesen")) {

      // Lesen aus dem externen Speicher (öffentliches Verzeichnis)

      // Prüfen, ob externer Speicher vorhanden und lesbar
      if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && !Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
        Toast.makeText(this,"Lesen nicht möglich: "+Environment.getExternalStorageState(), Toast.LENGTH_LONG).show();
        return;
      }

      BufferedReader bin = null;
      try {
        File extFilesPubDir = Environment.getExternalStoragePublicDirectory("");
        File inputFile = new File(extFilesPubDir.getAbsolutePath()+"/"+dateinameExternPublic);
        Toast.makeText(getApplicationContext(), "Lesen von "+inputFile.getPath()+" (extern/öffentlich)", Toast.LENGTH_LONG).show();
        bin = new BufferedReader(new FileReader(inputFile));
      } catch (Exception e) {
        Toast.makeText(this,"Fehler beim Öffnen: "+e.getClass(), Toast.LENGTH_LONG).show();
        Log.v("DEMO",">>>> Fehler beim Öffnen: "+e.getClass());
      }
      try {
        String eingabe = bin.readLine();
        Toast.makeText(this,"Gelesen: "+eingabe, Toast.LENGTH_LONG).show();
        Log.v("DEMO",">>>> Gelesen: "+eingabe);
      } catch (Exception e) {
        Toast.makeText(this,"Fehler beim Lesen: "+e.getClass(), Toast.LENGTH_LONG).show();
        Log.v("DEMO",">>>> Fehler beim Lesen: "+e.getClass());
      }
      try {
        bin.close();
      } catch (Exception e) {
        Toast.makeText(this,"Fehler beim Schließen: "+e.getClass(), Toast.LENGTH_LONG).show();
        Log.v("DEMO",">>>> Fehler beim Schließen: "+e.getClass());
      }
    }

    if (auswahl.equals("Info")) {
    	
      // Ausgabe von Informationen über das Dateisystem
    	
      File filesDir = this.getFilesDir();
      ausgabe = "FilesDir: "+filesDir.getAbsolutePath()+"\n\n";
      Log.v("DEMO",">>>> FilesDir: "+filesDir.getAbsolutePath());

      File cacheDir = this.getCacheDir();
      ausgabe += "CacheDir: "+cacheDir.getAbsolutePath()+"\n\n";
      Log.v("DEMO",">>>> CacheDir: "+cacheDir.getAbsolutePath());

      String externalStorageState = Environment.getExternalStorageState();
      Log.v("DEMO",">>>> ExternalStorageState: "+externalStorageState);
      ausgabe += "ExternalStorageState: "+externalStorageState+"\n\n";

      File externalFilesDir = this.getExternalFilesDir(null);
      if (externalFilesDir!=null) {
       Log.v("DEMO",">>>> ExternalFilesDir: "+externalFilesDir.getAbsolutePath());
       ausgabe += "ExternalFilesDir: "+externalFilesDir.getAbsolutePath()+"\n\n";
      }
      else {
       Log.v("DEMO",">>>> ExternalFilesDir: nicht vorhanden");
       ausgabe += "ExternalFilesDir: nicht vorhanden\n\n";
      }

      File externalPublicDir = Environment.getExternalStoragePublicDirectory("");
      if (externalPublicDir!=null) {
       Log.v("DEMO",">>>> ExternalStoragePublicDirectory: "+externalPublicDir.getAbsolutePath());
       ausgabe += "ExternalStoragePublicDirectory: "+externalPublicDir.getAbsolutePath()+"\n\n";
      }
      else {
       Log.v("DEMO",">>>> ExternalStoragePublicDirectory: nicht vorhanden");
       ausgabe += "ExternalStoragePublicDirectory: nicht vorhanden\n\n";
      }

//      File dataDir = Environment.getDataDirectory();
//      String dataDirPath = dataDir.getAbsolutePath();
//      ausgabe += "DataDirectory: "+dataDirPath+"\n\n";
//
//      Log.v("DEMO",">>>> DataDirectory: "+dataDirPath);
//      File rootDir = Environment.getRootDirectory();
//      String rootDirPath = rootDir.getAbsolutePath();
//
//      ausgabe += "RootDirectory: "+rootDirPath;
//      Log.v("DEMO",">>>> RootDirectory: "+rootDirPath);

      showDialog(0,null);

    }

  }

  // Dialog zur Ausgabe der Infos über die Dateisysteme

  @Override
  protected Dialog onCreateDialog(int id, Bundle bundle) {

    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
    dialogBuilder.setMessage(ausgabe);
    dialogBuilder.setCancelable(false);
    dialogBuilder.setPositiveButton("OK",null);
    AlertDialog dialog = dialogBuilder.create();
    return dialog;

  }

}

/*
  public void onStart() {
    super.onStart();
//    String exStorStat = Environment.getExternalStorageState();
//    Toast.makeText(this,"Environment.getExternalStorageState(): "+exStorStat, Toast.LENGTH_LONG).show();
//    Log.v("DEMO",">>>> Environment.getExternalStorageState(): "+exStorStat);
//    if (!exStorStat.equals("removed")) {
//        File exStorDir = Environment.getExternalStorageDirectory();
//        String exStorDirPath = exStorDir.getAbsolutePath();
//        Toast.makeText(this,"Pfad des ExternalStorageDirectory: "+exStorDirPath, Toast.LENGTH_LONG).show();
//        Log.v("DEMO",">>>> Pfad des ExternalStorageDirectory: "+exStorDirPath);
//        Log.v("DEMO",">>>> Inhalt des ExternalStorageDirectory:");
//        String filenames[] = exStorDir.list();
//        for (int i=0;i<filenames.length;i++)
//            Log.v("DEMO",">>>>   "+filenames[i]);
//    }
//    

  } 

} */