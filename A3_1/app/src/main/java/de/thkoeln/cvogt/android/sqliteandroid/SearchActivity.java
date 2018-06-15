package de.thkoeln.cvogt.android.sqliteandroid;

// Programmierung verteilter und mobiler Anwendungen
// Prof. Dr. Carsten Vogt
// TH Köln, Institut für Nachrichtentechnik
// Stand: 29.7.2016

// In "PersistenceAndroid" wurde die Adhoc-Definition einer Wörterbuch-SQL-Datenbank dargestellt.
// Hier wird die Wörterbuch-Datenbank anhand der systematischen Vorgehensweise definiert und benutzt,
// die in https://developer.android.com/training/basics/data-storage/databases.html beschrieben ist
// (Code von dort übernommen und angepasst).

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class SearchActivity extends Activity {

    int lang_from = 0; // 0=Deutsch-Englisch, 1=Englisch-Deutsch

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btn_lang_switch = (Button) findViewById(R.id.btn_lang_switch);
        btn_lang_switch.setOnClickListener(new Listener_btn_lang_switch());


        TextView lang_from = (TextView) findViewById(R.id.lang_from);
        TextView lang_to = (TextView) findViewById(R.id.lang_to);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menuitem_add:
                startActivity (new Intent (this, Add_Activity.class));
                return true;
            case R.id.menuitem_Export:
                if(db_Export()){
                    toast_show("Export erfolgreich!");
                    return true;
                }
                toast_show("Export FEHLGESCHLAGEN!");
                return false;
        }
        return false;
    }

    public boolean db_Export(){
        String result = "";


        WoerterbuchDbHelper woebuDbHelper = new WoerterbuchDbHelper(this);
        SQLiteDatabase db = woebuDbHelper.getReadableDatabase();

        String Col_1 = WoerterbuchContract.WoerterbuchEintraege.COLUMN_NAME_WORT_DEUTSCH;
        String Col_2 = WoerterbuchContract.WoerterbuchEintraege.COLUMN_NAME_WORT_ENGLISCH;
        String Col_3 = WoerterbuchContract.WoerterbuchEintraege.COLUMN_NAME_TIMESTEMP;

        String[] projection = {Col_1,Col_2,Col_3};

        // How you want the results sorted in the resulting Cursor
        String sortOrder = Col_1 + " ASC";


        // Gesamte Datenbank Abfragen
        Cursor ergCursor = db.query(
                WoerterbuchContract.WoerterbuchEintraege.TABLE_NAME,  // The table to query
                projection,                               // The columns to retur
                null,                              // WHERE claunse
                null,                                     // no values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if (ergCursor != null) {
            // Durchlaufen des Ergebnis-Cursors und Zusammenstellen der Ausgabe
            int wortIndex_1 = ergCursor.getColumnIndexOrThrow(Col_1);
            int wortIndex_2 = ergCursor.getColumnIndexOrThrow(Col_2);
            //int wortfinishIndex = ergCursor.getColumnIndexOrThrow(WoerterbuchContract.WoerterbuchEintraege.COLUMN_NAME_WORT_FINISH);
            int timestempIndex = ergCursor.getColumnIndexOrThrow(Col_3);

            result = "";
            if (ergCursor.moveToFirst()) {
                do {
                    String wortdeutschStr = ergCursor.getString(wortIndex_1);
                    String wortenglischStr = ergCursor.getString(wortIndex_2);
                    //String wortfinishStr = ergCursor.getString(wortfinishIndex);
                    String worttimestempStr = ergCursor.getString(timestempIndex);
                    result = result + wortdeutschStr + ";" + wortenglischStr + ";" /*+ wortfinishStr + ";"*/ + worttimestempStr + ";\n";
                } while (ergCursor.moveToNext());
            }
        }

        return writeToFile(result,this);
    }

    public boolean writeToFile(String data,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("db_output.txt",MODE_PRIVATE));
            Log.e("DB_Path", getFileStreamPath("db_output.txt").toString());
            outputStreamWriter.write(data);
            outputStreamWriter.close();

            return true;
        }
        catch (Exception e) {
            Log.e("Exception", "File write failed: " + e.toString());
            return false;
        }
    }

    public void toast_show(String text){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    // Suche in der Datenbank (d.h. Lesen aus der DB)
    public String db_ausgabe(String col_1, String col_2, String col_3, SQLiteDatabase db, String suchwort) {
        String result = "";

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                col_1,col_2,col_3
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = col_1 + " ASC";

        // Where-Clause spezifiziert die Eigenschaften der gesuchten DB-Einträge
        String whereClause;
        if (!suchwort.contains("%"))  // Abfrage mit Wildcard?
            whereClause = col_1
                    + " = '" + suchwort + "'";
        else
            whereClause = col_1
                    + " like '" + suchwort + "'";


        Cursor ergCursor = db.query(
                WoerterbuchContract.WoerterbuchEintraege.TABLE_NAME,  // The table to query
                projection,                               // The columns to retur
                whereClause,                              // WHERE claunse
                null,                                     // no values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if (ergCursor != null) {
            // Durchlaufen des Ergebnis-Cursors und Zusammenstellen der Ausgabe
            int wortIndex_1 = ergCursor.getColumnIndexOrThrow(col_1);
            int wortIndex_2 = ergCursor.getColumnIndexOrThrow(col_2);
            //int wortfinishIndex = ergCursor.getColumnIndexOrThrow(WoerterbuchContract.WoerterbuchEintraege.COLUMN_NAME_WORT_FINISH);
            int timestempIndex = ergCursor.getColumnIndexOrThrow(col_3);

            result = "";
            if (ergCursor.moveToFirst()) {
                do {
                    String wortdeutschStr = ergCursor.getString(wortIndex_1);
                    String wortenglischStr = ergCursor.getString(wortIndex_2);
                    //String wortfinishStr = ergCursor.getString(wortfinishIndex);
                    String worttimestempStr = ergCursor.getString(timestempIndex);
                    result = result + wortdeutschStr + " : " + wortenglischStr + " : " /*+ wortfinishStr + " : "*/ + worttimestempStr + "\n";
                } while (ergCursor.moveToNext());
            }


            return result;
        }

        return result;
    }

    public void dbSuche(View v) {

        TextView sucheingabefeld = (TextView) findViewById(R.id.sucheingabe);
        String suchwort = sucheingabefeld.getText().toString();  // gesuchtes Wort gemäß Benutzereingabe

        // Öffnen der Datenbank zum Lesen
        WoerterbuchDbHelper woebuDbHelper = new WoerterbuchDbHelper(this);
        SQLiteDatabase db = woebuDbHelper.getReadableDatabase();

        //woebuDbHelper.onUpgrade(db,WoerterbuchDbHelper.DATABASE_VERSION,WoerterbuchDbHelper.DATABASE_VERSION+1);




        // AUSLAGERN START #########################################################################
            String Col_1="",Col_2="",Col_3="";

            if (lang_from==0) {
               Col_1 = WoerterbuchContract.WoerterbuchEintraege.COLUMN_NAME_WORT_DEUTSCH;
               Col_2 = WoerterbuchContract.WoerterbuchEintraege.COLUMN_NAME_WORT_ENGLISCH;

            } else if (lang_from==1) {
               Col_1 = WoerterbuchContract.WoerterbuchEintraege.COLUMN_NAME_WORT_ENGLISCH;
               Col_2 = WoerterbuchContract.WoerterbuchEintraege.COLUMN_NAME_WORT_DEUTSCH;
            }


            Col_3 = WoerterbuchContract.WoerterbuchEintraege.COLUMN_NAME_TIMESTEMP;
            String result = db_ausgabe(Col_1,Col_2,Col_3,db,suchwort);

        // AUSLAGERN ENDE#########################################################################


            // Ausgabe des Ergebnisses
            TextView suchergebnisfeld = (TextView) findViewById(R.id.suchergebnis);
            suchergebnisfeld.setText(result);

        // Zum Schreiben in die DB:
        // SQLiteDatabase db = woebuDbHelper.getWritableDatabase();
        // und dann Vorgehensweise wie in WoerterbuchDbHelper.onCreate()
        }

    class Listener_btn_lang_switch implements View.OnClickListener {   // Listener des Buttons
        TextView tv_lang_from = (TextView) findViewById(R.id.lang_from);
        TextView tv_lang_to = (TextView) findViewById(R.id.lang_to);
        public void onClick(View v) {

            if(lang_from==0){
                lang_from=1;
                tv_lang_from.setText("Englisch");
                tv_lang_to.setText("Deutsch");
            }else{
                lang_from=0;
                tv_lang_from.setText("Deutsch");
                tv_lang_to.setText("Englisch");


            }


        }
    }

}
