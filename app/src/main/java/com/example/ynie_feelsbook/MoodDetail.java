package com.example.ynie_feelsbook;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class MoodDetail extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    private ArrayList<Mood> moods = new ArrayList<Mood>();
    private int position;
    private EditText commentBox;
    private Spinner moodType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_detail);
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);

        commentBox = findViewById(R.id.comment);
        moodType = findViewById(R.id.moodtype);
        Button saveButton = (Button) findViewById(R.id.save);
        Button deleteButton = (Button) findViewById(R.id.delete);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMood();
                saveInFile();
                finish();


            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    saveMood();
                }catch (tooLongMessageException e){
                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.comment), R.string.messageTooLong, Snackbar.LENGTH_SHORT);
                    mySnackbar.show();
                    //snackbar implementation from:https://developer.android.com/training/snackbar/showing
                }
                deleteMood();
                saveInFile();
                finish();


            }
        });

    }
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        loadFromFile();
        Collections.sort(moods);
        Mood mood = moods.get(position);

        String message = mood.getMessage();
        commentBox.setText(message);

        Log.d("check", Integer.toString(position));


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
    private void saveInFile(){
        try {
            FileOutputStream fos = openFileOutput(FILENAME,0);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(moods,osw);
            osw.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private void deleteMood(){
        moods.remove(position);
    }
    private void saveMood()throws tooLongMessageException{
        try{
            Mood mood = moods.get(position);
            Mood newMood = new Fear(mood.getDate());
            newMood.setName(mood.getName());
            newMood.setMessage(commentBox.getText().toString());
            moods.add(newMood);
        } catch (tooLongMessageException e){

        }


    }
}
