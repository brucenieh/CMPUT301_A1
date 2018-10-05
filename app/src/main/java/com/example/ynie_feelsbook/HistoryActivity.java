package com.example.ynie_feelsbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class HistoryActivity extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    private ArrayList<Mood> moods = new ArrayList<Mood>();
    private ArrayAdapter<Mood> adapter;
    private ListView history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent intent = getIntent();


        history = findViewById(R.id.historyList);
        history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToDetail(position);

            }
        });

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        loadFromFile();
        Collections.sort(moods);
        adapter = new ArrayAdapter<Mood>(this,R.layout.list_item, moods);
        history.setAdapter(adapter);


    }
    private void loadFromFile() {

        try {
            FileInputStream fis = openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader read = new BufferedReader(isr);
            Gson gson = new Gson();
            Type typeList = new TypeToken<ArrayList<Fear>>(){}.getType();
            moods = gson.fromJson(read, typeList);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void goToDetail(int position){
        //Log.d("check",moods.get(position).toString());
        Intent intent = new Intent(this, MoodDetail.class);
        intent.putExtra("position",position);
        startActivity(intent);
    }
}
