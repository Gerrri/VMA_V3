package de.thkoeln.cvogt.android.sqliteandroid;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Add_Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        EditText et_deu = (EditText) findViewById(R.id.editText_deu);
        EditText et_eng = (EditText) findViewById(R.id.editText_eng);

        Button btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new Listener_btn_add());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menuitem_search:
                startActivity (new Intent(this, SearchActivity.class));
                return true;
        }

        return false;
    }

    class Listener_btn_add implements View.OnClickListener {   // Listener des Buttons
        EditText et_deu = (EditText) findViewById(R.id.editText_deu);
        EditText et_eng = (EditText) findViewById(R.id.editText_eng);

        public void onClick(View v) {
            // Zum Schreiben in die DB:
            // SQLiteDatabase db = woebuDbHelper.getWritableDatabase();
            // und dann Vorgehensweise wie in WoerterbuchDbHelper.onCreate()

            WoerterbuchDbHelper woebuDbHelper = new WoerterbuchDbHelper(v.getContext());
            SQLiteDatabase db = woebuDbHelper.getWritableDatabase();

            String[] insert_string = {et_deu.getText().toString(), et_eng.getText().toString(), "N/A"};


            if (insert_string[0].length() != 0 && insert_string[1].length() != 0) {


            ContentValues values = new ContentValues();
            values.put(WoerterbuchContract.WoerterbuchEintraege.COLUMN_NAME_WORT_DEUTSCH, insert_string[0]);
            values.put(WoerterbuchContract.WoerterbuchEintraege.COLUMN_NAME_WORT_ENGLISCH, insert_string[1]);
            values.put(WoerterbuchContract.WoerterbuchEintraege.COLUMN_NAME_WORT_FINISH, insert_string[2]);
            values.put(WoerterbuchContract.WoerterbuchEintraege.COLUMN_NAME_TIMESTEMP, System.currentTimeMillis());

            // Insert the new row, returning the primary key value of the new row
            long newRowId;
            newRowId = db.insert(WoerterbuchContract.WoerterbuchEintraege.TABLE_NAME, null, values);


            Context context = getApplicationContext();
            CharSequence text = "Datensatz Hinzugefügt!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            }else{
            Context context = getApplicationContext();
            CharSequence text = "Fehler: Daten nicht Valide!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            }




        }
    }



}
