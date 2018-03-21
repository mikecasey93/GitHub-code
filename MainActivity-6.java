package com.example.michaelcasey.musicapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.attr.id;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView lv = (ListView) findViewById(R.id.lview1);
        ArrayList<String> values = queryDB();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,values);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent details = new Intent(getApplicationContext(), Main2Activity.class);
                String artist = ((TextView)view).getText().toString();

                artist = artist.substring(0, artist.indexOf('-'));
                details.putExtra("artistID", artist);
                startActivity(details);
            }
        });
    }

    ArrayList<String> queryDB() {

        ArrayList<String> list = new ArrayList<String>();
        Cursor cursor;
        try {
            SQLiteDatabase db = openOrCreateDatabase("Chinook_Sqlite.sqlite", Context.MODE_PRIVATE, null);
            cursor = db.rawQuery("select ArtistId, name  from artist", null);
            while(cursor.moveToNext()) {
                String n = cursor.getString(0);
                String r = cursor.getString(1);
                list.add(n + "-" + r);

            }
            cursor.close();
            db.close();
        }catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error in opening DB", Toast.LENGTH_LONG);
        }

        return list;
    }
}
