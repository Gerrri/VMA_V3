package com.example.gerri.a3_2;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;






public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_save = findViewById(R.id.btn_save);
        Button btn_clear = findViewById(R.id.btn_clear);
        Button btn_restore = findViewById(R.id.btn_restore);

        btn_save.setOnClickListener(new Listener_btn_save());
        btn_clear.setOnClickListener(new Listener_btn_clear());
        btn_restore.setOnClickListener(new Listener_btn_restore());

    }



    class Listener_btn_save implements View.OnClickListener {   // Listener des Buttons
        EditText et_int = findViewById(R.id.et_int);
        EditText et_double = findViewById(R.id.et_double);
        EditText et_string = findViewById(R.id.et_string);



        public void onClick(View v) {

            SharedPreferences.Editor editor = getSharedPreferences("SP", MODE_PRIVATE).edit();


           if(et_int.getText().length()>0 && et_double.getText().length()>0 && et_string.getText().length()>0){
               editor.putInt("et_int",(Integer.parseInt(et_int.getText().toString())));
               editor.putFloat("et_double",Float.parseFloat(et_double.getText().toString()));
               editor.putString("et_string",et_string.getText().toString());

               editor.apply();

               Context context = getApplicationContext();
               int duration = Toast.LENGTH_SHORT;

               Toast toast = Toast.makeText(context, "Eingabe gespeichert!", duration);
               toast.show();



           }else{
               editor.putInt("et_int", -1);
               editor.putFloat("et_double",-1);
               editor.putString("et_string","N/A");

               if(et_int.getText().length()>0) { editor.putInt("et_int",(Integer.parseInt(et_int.getText().toString()))); }
               if(et_double.getText().length()>0) {editor.putFloat("et_double",Float.parseFloat(et_double.getText().toString()));}
               if(et_string.getText().length()>0) {editor.putString("et_string",et_string.getText().toString());}

               editor.apply();

               Context context = getApplicationContext();
               int duration = Toast.LENGTH_SHORT;

               Toast toast = Toast.makeText(context, "Es wurde Leere werte Übertragen", duration);
               toast.show();

           }


        }
    }
    class Listener_btn_clear implements View.OnClickListener {   // Listener des Buttons
        EditText et_int = findViewById(R.id.et_int);
        EditText et_double = findViewById(R.id.et_double);
        EditText et_string = findViewById(R.id.et_string);

        public void onClick(View v) {
            et_int.setText("");
            et_double.setText("");
            et_string.setText("");

            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, "Eingabe gelöscht!", duration);
            toast.show();
        }
    }
    class Listener_btn_restore implements View.OnClickListener {   // Listener des Buttons
        EditText et_int = findViewById(R.id.et_int);
        EditText et_double = findViewById(R.id.et_double);
        EditText et_string = findViewById(R.id.et_string);

        public void onClick(View v) {
            SharedPreferences prefs = getSharedPreferences("SP", MODE_PRIVATE);

            et_int.setText(String.valueOf(prefs.getInt("et_int",-1)));
            et_double.setText((String.valueOf(prefs.getFloat("et_double", -1))));
            et_string.setText(prefs.getString("et_string","No String Defined"));

            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, "aus SharedPreference geladen", duration);
            toast.show();

        }
    }
}
