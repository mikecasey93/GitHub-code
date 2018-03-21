package com.example.michaelcasey.musicapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Intent intent = getIntent();

        String albumid = intent.getStringExtra("artist2");

        ListView lv = (ListView) findViewById(R.id.MP);
        ArrayList<String> values = queryDB(albumid);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,values);

        lv.setAdapter(adapter);
    }

    ArrayList<String> queryDB(String albumid){

        ArrayList<String> list = new ArrayList<String>();
        Cursor cursor;
        try{
            SQLiteDatabase db = openOrCreateDatabase("Chinook_Sqlite.sqlite", Context.MODE_PRIVATE, null);
            cursor = db.rawQuery("select TrackId, Name, Composer from Track where AlbumId = '" + albumid +"'",null);
            while(cursor.moveToNext()){
                String a = cursor.getString(0);
                String b = cursor.getString(1);
                String c = cursor.getString(2);
                list.add(a+" "+b+" "+c);
            }
            cursor.close();
            db.close();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error in opening DB", Toast.LENGTH_LONG);
        }
        return list;
    }
}
