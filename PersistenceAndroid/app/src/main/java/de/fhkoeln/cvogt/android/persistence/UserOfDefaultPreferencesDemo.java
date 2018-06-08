package de.fhkoeln.cvogt.android.persistence;

// Programmierung verteilter und mobiler Anwendungen
// Prof. Dr. Carsten Vogt
// TH Köln, Institut für Nachrichtentechnik
// Stand: 11.02.2016

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class UserOfDefaultPreferencesDemo extends Activity {

    SharedPreferences defaultSharedPreferences;
    SharedPreferences.OnSharedPreferenceChangeListener prefChangeListener;  // zur Notwendigkeit dieser Variablen siehe http://stackoverflow.com/questions/2542938/sharedpreferences-onsharedpreferencechangelistener-not-being-called-consistently
    TextView ausgabeCheckboxpref, ausgabeListpref, ausgabeEditextpref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.userofdefaultpreferences);

        ausgabeCheckboxpref = (TextView) findViewById(R.id.ausgabeCheckboxpref);
        ausgabeListpref = (TextView) findViewById(R.id.ausgabeListpref);
        ausgabeEditextpref = (TextView) findViewById(R.id.ausgabeEdittextpref);

        defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ausgabeCheckboxpref.setText(" >> checkboxpref:  " + defaultSharedPreferences.getBoolean("checkboxpref", false));
        ausgabeListpref.setText(" >> listpref:  " + defaultSharedPreferences.getString("listpref", "--"));
        ausgabeEditextpref.setText(" >> edittextpref:  " + defaultSharedPreferences.getString("edittextpref", "--"));

        prefChangeListener =
                new SharedPreferences.OnSharedPreferenceChangeListener() {
                    @Override
                    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                        Log.v("DEMO", "Preference " + s + " modified");
                        if (s.equals("checkboxpref")) {
                            Log.v("DEMO", "Neuer Wert von 'checkboxpref': " + sharedPreferences.getBoolean("checkboxpref", false));
                            ausgabeCheckboxpref.setText(" >> checkboxpref:  " + sharedPreferences.getBoolean("checkboxpref", false));
                            ausgabeCheckboxpref.setTextColor(Color.parseColor("#FF0000"));
                        }
                        if (s.equals("listpref")) {
                            Log.v("DEMO", "listpref: " + sharedPreferences.getString("listpref", "--"));
                            ausgabeListpref.setText(" >> listpref:  " + sharedPreferences.getString("listpref", "--"));
                            ausgabeListpref.setTextColor(Color.parseColor("#FF0000"));
                            ;
                        }
                        if (s.equals("edittextpref")) {
                            Log.v("DEMO", "edittextpref: " + sharedPreferences.getString("edittextpref", "--"));
                            ausgabeEditextpref.setText(" >> edittextpref:  " + sharedPreferences.getString("edittextpref", "--"));
                            ausgabeEditextpref.setTextColor(Color.parseColor("#FF0000"));
                        }
                    }
                };

        defaultSharedPreferences.registerOnSharedPreferenceChangeListener(prefChangeListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.userofdefaultpreferences, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.showpreferences) {
            startActivity (new Intent(this, PreferenceActivityDemo.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
