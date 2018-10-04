package com.example.ynie_feelsbook;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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

        }catch (tooLongMessageException e){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.comment), R.string.messageTooLong, Snackbar.LENGTH_SHORT);
            mySnackbar.show();
            //snackbar implementation from:https://developer.android.com/training/snackbar/showing
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


}
