package com.example.gerri.a3_3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Set;

public class Activity_act_2 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_2);

        // Abfrage und Anzeige von Informationen über den Intent, der die Activity gestartet hat
        Intent intent = getIntent();
        String action = intent.getAction();
        Set<String> categories = intent.getCategories();

        Toast.makeText(this, "This is Action 2" ,Toast.LENGTH_SHORT).show();

    }
}
