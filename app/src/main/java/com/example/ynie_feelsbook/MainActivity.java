package com.example.ynie_feelsbook;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
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
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    /** shows the first view when users launch the app
     * Has six buttons corresponding to six emotions that act as save buttons to save the mood into the file specified
     * Has one edittext for taking user inputs as optional comment for the mood input
     * Has six counters indicating the number of moods that have been recorded,
     * the counters update everytime the user add a new mood
     * has a button at the bottom for switching to HistoryActivity
     */
    private static final String FILENAME = "file.sav";
    private ArrayList<Mood> moods = new ArrayList<Mood>();
    private EditText bodyText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button joyButton = (Button) findViewById(R.id.joy);
        joyButton.setOnClickListener(this);

        Button loveButton = (Button) findViewById(R.id.love);
        loveButton.setOnClickListener(this);

        Button surpriseButton = (Button) findViewById(R.id.surprise);
        surpriseButton.setOnClickListener(this);

        Button fearButton = (Button) findViewById(R.id.fear);
        fearButton.setOnClickListener(this);

        Button saddnessButton = (Button) findViewById(R.id.sadness);
        saddnessButton.setOnClickListener(this);

        Button angerButton = (Button) findViewById(R.id.anger);
        angerButton.setOnClickListener(this);

        bodyText = (EditText) findViewById(R.id.comment);

        Button historyButton = (Button) findViewById(R.id.history);
        historyButton.setOnClickListener(this);



    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        loadFromFile();
        updateAllCounter();


    }
    @Override/*
    taking suggestions from answer from stackoverflow to implement multiple button onclicklisteners
    url: https://stackoverflow.com/questions/25905086/multiple-buttons-onclicklistener-android
    */ public void onClick(View v){
        /** catches button clicks
         * and save the button's corresponding mood into arraylist
         */

        Mood newMood = null;
        switch(v.getId()){

            case R.id.history:
                Intent intent = new Intent(this, HistoryActivity.class);
                startActivity(intent);
                return;

            case R.id.joy:
                newMood = new Joy(new Date());
                break;

            case R.id.love:
                newMood = new Love(new Date());
                break;

                case R.id.surprise:
                    newMood = new Surprise(new Date());
                    break;

            case R.id.sadness:
                newMood = new Sadness(new Date());
                break;

            case R.id.fear:
                newMood = new Fear(new Date());
                break;

            case R.id.anger:
                newMood = new Anger(new Date());
                break;
                }
        try{
            String message = bodyText.getText().toString();
            Log.d("check",message);
            newMood.setMessage(message);
            moods.add(newMood);
            saveInFile();
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.comment), R.string.MoodSaved, Snackbar.LENGTH_LONG);
            mySnackbar.show();
            updateAllCounter();

        }catch (tooLongMessageException e){
            Snackbar snackbar = Snackbar.make(findViewById(R.id.comment), R.string.messageTooLong, Snackbar.LENGTH_SHORT);
            snackbar.show();

        }

    }

    private void loadFromFile() {
        /**
         * load the json file ad put the content into a arraylist
         */

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
        /**
         * save all item in the ArrayList into a json file
         */
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

    private int countMood(Mood moodToMatch){
        /**
         * counts the number of mood that has appeared in the mood array
         */
        int counter = 0;

        for(Mood mood:moods){
            if (mood.getName().equals(moodToMatch.getName())){



                counter++;

            }
        }
        return counter;
    }

    private void updateAllCounter(){
        /**
         * update all mood counters
         */
        updateTotalCountFear();
        updateTotalCountAnger();
        updateTotalCountJoy();
        updateTotalCountLove();
        updateTotalCountSadness();
        updateTotalCountSurprise();
    }

    private void updateTotalCountFear(){
        int counter = 0;
        counter = countMood(new Fear(new Date()));
        TextView text = (TextView)findViewById(R.id.fearCounter);
        text.setText("Fear: " + Integer.toString(counter));

    }

    private void updateTotalCountAnger(){
        int counter = 0;
        counter = countMood(new Anger(new Date()));
        TextView text = (TextView)findViewById(R.id.angerCounter);
        text.setText("Anger: " + Integer.toString(counter));

    }

    private void updateTotalCountJoy(){
        int counter = 0;
        counter = countMood(new Joy(new Date()));
        TextView text = (TextView)findViewById(R.id.joyCounter);
        text.setText("Joy: " + Integer.toString(counter));

    }

    private void updateTotalCountLove(){
        int counter = 0;
        counter = countMood(new Love(new Date()));
        TextView text = (TextView)findViewById(R.id.loveCounter);
        text.setText("Love: " + Integer.toString(counter));

    }

    private void updateTotalCountSurprise(){
        int counter = 0;
        counter = countMood(new Surprise(new Date()));
        TextView text = (TextView)findViewById(R.id.surpriseCounter);
        text.setText("Surprise: " + Integer.toString(counter));

    }

    private void updateTotalCountSadness(){
        int counter = 0;
        counter = countMood(new Sadness(new Date()));
        TextView text = (TextView)findViewById(R.id.sadnessCounter);
        text.setText("Sadness: " + Integer.toString(counter));

    }


}
