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

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();

        String artist = intent.getStringExtra("artistID");

        ListView lv = (ListView) findViewById(R.id.QB);
        ArrayList<String> values = queryDB(artist);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,values);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent2, View view2, int position2, long id2)
            {
                String album = ((TextView)view2).getText().toString();
                Intent artist2 = new Intent(getApplicationContext(), Main3Activity.class);
                album = album.substring(0, album.indexOf('-'));
                artist2.putExtra("artist2", album);
                startActivity(artist2);
            }
        });

    }

    ArrayList<String> queryDB(String artist){

        ArrayList<String> list = new ArrayList<String>();
        Cursor cursor;
        try{
            SQLiteDatabase db = openOrCreateDatabase("Chinook_Sqlite.sqlite", Context.MODE_PRIVATE, null);
            cursor = db.rawQuery("select AlbumId, title from Album where ArtistId = '" + artist +"'",null);
            while (cursor.moveToNext()) {
                String n = cursor.getString(0);
                String m = cursor.getString(1);
                list.add(n+"-"+m);
            }
            cursor.close();
            db.close();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error in opening DB", Toast.LENGTH_LONG);
        }
        return list;
    }
}
