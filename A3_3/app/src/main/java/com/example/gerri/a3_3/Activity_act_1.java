package com.example.gerri.a3_3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Set;

public class Activity_act_1 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_1);

        // Abfrage und Anzeige von Informationen über den Intent, der die Activity gestartet hat
        Intent intent = getIntent();
        String action = intent.getAction();
        Set<String> categories = intent.getCategories();

        Toast.makeText(this, "This is Action 1" ,Toast.LENGTH_SHORT).show();


        //auslesen der Message - NUR EIN TEST !
        //Log.v("DEMO",">>> Extras: "+intent.getStringExtra("Message"));


    }
}
