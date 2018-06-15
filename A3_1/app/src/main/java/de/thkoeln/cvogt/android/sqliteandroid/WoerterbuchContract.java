package de.thkoeln.cvogt.android.sqliteandroid;

// Programmierung verteilter und mobiler Anwendungen
// Prof. Dr. Carsten Vogt
// TH Köln, Institut für Nachrichtentechnik
// Stand: 29.7.2016

// In "PersistenceAndroid" wurde die Adhoc-Definition einer Wörterbuch-SQL-Datenbank dargestellt.
// Hier wird die Wörterbuch-Datenbank anhand der systematischen Vorgehensweise definiert und benutzt,
// die in https://developer.android.com/training/basics/data-storage/databases.html beschrieben ist
// (Code von dort übernommen und angepasst).

import android.provider.BaseColumns;

// https://developer.android.com/training/basics/data-storage/databases.html:
// "You may find it helpful to create a companion class, known as a contract class,
// which explicitly specifies the layout of your schema in a systematic and self-documenting way"
// "A good way to organize a contract class is to put definitions that are global to your whole database in the root level of the class.
// Then create an inner class for each table that enumerates its columns."

public final class WoerterbuchContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.

    public WoerterbuchContract() {}

    // Inner class that defines the table contents
    // https://developer.android.com/training/basics/data-storage/databases.html:
    // "By implementing the BaseColumns interface,
    // your inner class can inherit a primary key field called _ID."

    public static abstract class WoerterbuchEintraege implements BaseColumns {
        public static final String TABLE_NAME = "DeutschEnglisch";
        public static final String COLUMN_NAME_WORT_DEUTSCH = "WortDeutsch";
        public static final String COLUMN_NAME_WORT_ENGLISCH = "WortEnglisch";
        public static final String COLUMN_NAME_WORT_FINISH = "WortFinish";
        public static final String COLUMN_NAME_TIMESTEMP = "Timestempt";
    }

}