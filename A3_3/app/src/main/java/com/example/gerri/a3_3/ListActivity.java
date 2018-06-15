package com.example.gerri.a3_3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;



public class ListActivity extends Activity {

    // Define string array.
    String[] listItemsValue = new String[] {"Implizit 1", "Implizit 2"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView lv = (ListView) findViewById(R.id.lv_intent);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, listItemsValue);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView <?> parent, View v, int position, long id){
                ListView lv = findViewById(R.id.lv_intent);

                //Toast.makeText(ListActivity.this, listItemsValue[position],Toast.LENGTH_SHORT).show();

                switch(position){
                    case 0: impli_1();
                            break;
                    case 1: impli_2();
                            break;
                }

                Object o = parent.getItemAtPosition(position);
            }
        });

    }



    public void impli_1(){
        Intent intent = new Intent("MY_ACTION_1");
        intent.putExtra("Message","This is Action 1");
        startActivity(intent);
    }

    public void impli_2(){
        Intent intent = new Intent("MY_ACTION_2");
        intent.putExtra("Message","This is Action 2");
        startActivity(intent);
    }




}
