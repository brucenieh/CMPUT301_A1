package com.example.ynie_feelsbook;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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


    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        loadFromFile();

    }
    @Override//taking suggestions from answer from stackoverflow to implement multiple button onclicklisteners
    // url: https://stackoverflow.com/questions/25905086/multiple-buttons-onclicklistener-android
    public void onClick(View v){
        Mood newMood = null;
        switch(v.getId()){

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
            newMood.setMessage(message);
            moods.add(newMood);
            saveInFile();
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.comment), R.string.MoodSaved, Snackbar.LENGTH_LONG);
            mySnackbar.show();
            updateAllCounter();

        }catch (tooLongMessageException e){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.comment), R.string.messageTooLong, Snackbar.LENGTH_SHORT);
            mySnackbar.show();
            //snackbar implementation from:https://developer.android.com/training/snackbar/showing
        }

    }

    private void loadFromFile() {

        try {
            FileInputStream fis = openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader read = new BufferedReader(isr);
            Gson gson = new Gson();
            Type typeList = new TypeToken<ArrayList<Mood>>(){}.getType();
            moods = gson.fromJson(read, typeList);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
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

    public int countMood(Mood moodToMatch){
        int counter = 0;
        for(Mood mood:moods){
            if (mood.getClass() == moodToMatch.getClass()){
                counter++;
            }
        }
        return counter;
    }

    public void updateAllCounter(){
        updateTotalCountFear();
        updateTotalCountAnger();
        updateTotalCountJoy();
        updateTotalCountLove();
        updateTotalCountSadness();
        updateTotalCountSurprise();
    }

    public void updateTotalCountFear(){
        int counter = 0;
        counter = countMood(new Fear());
        TextView text = (TextView)findViewById(R.id.fearCounter);
        text.setText("Fear: " + Integer.toString(counter));

    }

    public void updateTotalCountAnger(){
        int counter = 0;
        counter = countMood(new Anger());
        TextView text = (TextView)findViewById(R.id.angerCounter);
        text.setText("Anger: " + Integer.toString(counter));

    }

    public void updateTotalCountJoy(){
        int counter = 0;
        counter = countMood(new Joy());
        TextView text = (TextView)findViewById(R.id.joyCounter);
        text.setText("Joy: " + Integer.toString(counter));

    }

    public void updateTotalCountLove(){
        int counter = 0;
        counter = countMood(new Love());
        TextView text = (TextView)findViewById(R.id.loveCounter);
        text.setText("Love: " + Integer.toString(counter));

    }

    public void updateTotalCountSurprise(){
        int counter = 0;
        counter = countMood(new Surprise());
        TextView text = (TextView)findViewById(R.id.surpriseCounter);
        text.setText("Surprise: " + Integer.toString(counter));

    }

    public void updateTotalCountSadness(){
        int counter = 0;
        counter = countMood(new Sadness());
        TextView text = (TextView)findViewById(R.id.sadnessCounter);
        text.setText("Sadness: " + Integer.toString(counter));

    }


}
