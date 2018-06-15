package de.thkoeln.cvogt.android.sqliteandroid;

// Programmierung verteilter und mobiler Anwendungen
// Prof. Dr. Carsten Vogt
// TH Köln, Institut für Nachrichtentechnik
// Stand: 29.7.2016

// In "PersistenceAndroid" wurde die Ad-Hoc-Definition einer Wörterbuch-SQL-Datenbank dargestellt.
// Hier wird die Wörterbuch-Datenbank anhand der systematischen Vorgehensweise definiert und benutzt,
// die in https://developer.android.com/training/basics/data-storage/databases.html beschrieben ist
// (Code von dort übernommen und angepasst).

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// https://developer.android.com/training/basics/data-storage/databases.html:
// "Create a Database Using a SQL Helper:
// "... implement methods that create and maintain the database and tables.
// To use SQLiteOpenHelper, create a subclass that overrides the onCreate(), onUpgrade() and onOpen()
// callback methods. You may also want to implement onDowngrade(), but it's not required."
// (Anmerkung: Entgegen diesem Kommentar muss auch onOpen() nicht implementiert werden, da nicht abstrakt.)

public class WoerterbuchDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Woerterbuch.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + WoerterbuchContract.WoerterbuchEintraege.TABLE_NAME + " (" +
                    WoerterbuchContract.WoerterbuchEintraege._ID + " INTEGER PRIMARY KEY," +
                    WoerterbuchContract.WoerterbuchEintraege.COLUMN_NAME_WORT_DEUTSCH + TEXT_TYPE + COMMA_SEP +
                    WoerterbuchContract.WoerterbuchEintraege.COLUMN_NAME_WORT_ENGLISCH + TEXT_TYPE + COMMA_SEP +
                    WoerterbuchContract.WoerterbuchEintraege.COLUMN_NAME_WORT_FINISH + TEXT_TYPE + COMMA_SEP +
                    WoerterbuchContract.WoerterbuchEintraege.COLUMN_NAME_TIMESTEMP + TEXT_TYPE +
            " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + WoerterbuchContract.WoerterbuchEintraege.TABLE_NAME;

    public WoerterbuchDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        // erzeugt die Wörterbuch-Tabelle
        db.execSQL(SQL_CREATE_TABLE);
        // Initialwerte für die Datenbank
        String[][] woebuInitwerte_Deu_Eng = { {"Auto","car","auto",""}, {"Haus","house","talo",""}, {"Haus","home","koti",""}, {"Hausfrau","housewife","kotiäiti",""},
                {"freistehendes Haus","detached house","omakotitalo",""}, {"in ein Haus einbrechen","to burgle a house","murtautua taloon",""} };

        // füllt die Tabelle mit den Initialwerten DEU_ENG
        for (int i=0;i< woebuInitwerte_Deu_Eng.length;i++) {
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(WoerterbuchContract.WoerterbuchEintraege.COLUMN_NAME_WORT_DEUTSCH, woebuInitwerte_Deu_Eng[i][0]);
            values.put(WoerterbuchContract.WoerterbuchEintraege.COLUMN_NAME_WORT_ENGLISCH, woebuInitwerte_Deu_Eng[i][1]);
            values.put(WoerterbuchContract.WoerterbuchEintraege.COLUMN_NAME_WORT_FINISH, woebuInitwerte_Deu_Eng[i][2]);
            values.put(WoerterbuchContract.WoerterbuchEintraege.COLUMN_NAME_TIMESTEMP, System.currentTimeMillis());

            // Insert the new row, returning the primary key value of the new row
            long newRowId;
            newRowId = db.insert(WoerterbuchContract.WoerterbuchEintraege.TABLE_NAME,null,values);
        }

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The upgrade policy is to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }





}