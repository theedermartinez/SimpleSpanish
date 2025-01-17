/*
* Author: Eder Martinez
* Objective: This is the first auto generates class. Starting screen
* we might use this as a splash screen at some point
* I added it to start the high scores at 0 if they do not exist in the SHAREDPRFERENCES
* */
package com.example.simplespanish;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //create button
        Button start = findViewById(R.id.start_buttonid);
        start.setSoundEffectsEnabled(false);

        start.setOnClickListener(v ->{
            Intent go = new Intent(MainActivity.this, MainMenu.class);
            startActivity(go);
        });
        //turn sound off
        getWindow().getDecorView().setSoundEffectsEnabled(false);

        //array of every quiz i want to do a txt file someday but
        //for now this will work
        int[] quizScores = startQuizScoreArray();
        String[] quizNames = startQuizNameArray();
        startHighScores(quizNames,quizScores);




    }

    private int[] startQuizScoreArray()
    {
        int[] quizArray = new int[20];
        quizArray[0] = 0;//a_0_basic_greetings_menu_quiz.xml
        quizArray[1] = 0;//a_1_nouns_and_articles_menu_quiz.xml
        return quizArray;
    }

    private String[] startQuizNameArray()
    {
        String[] names = new String[20];
        names[0] = "a_0_basic_greetings_menu_quiz";//a_0_basic_greetings_menu_quiz
        names[1] = "a_0_basic_greetings_menu_quiz";//a_1_nouns_and_articles_menu_quiz
        return names;
    }



    private void startHighScores(String[] names, int[] scores)
    {
        //initalize shared preferences ITS AN ACTITY SO MUST USE GRE PREFERENCES NOACTITY SINCE ACTITITY IS THE JAVA ITSELF
        SharedPreferences shared = getSharedPreferences("HighScores", Context.MODE_PRIVATE);//name is the name of the file
        //call on the editor
        SharedPreferences.Editor edit = shared.edit();

        //loop to get all of the scores
        for(int i =0; i< names.length; i++)
        {
            if(!shared.contains(names[i]))//check if key is in it
            {
                edit.putInt(names[i], scores[i]);//we add them key and value
            }
        }
        edit.apply();

    }



}