/*
* Author: Eder Martinez
* Purpose: To change score and display it
*
* */

package com.example.simplespanish;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

public class A_0_BasicGreetings_menu_quiz_zend extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for old displays
        EdgeToEdge.enable(this);
        setContentView(R.layout.a_0_basic_greetings_menu_quiz_zend);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.a_0_main_quiz_zend), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //button listener and action
        Button homeButton = findViewById(R.id.quiz0_return_home_buttonid);
        homeButton.setOnClickListener(v -> {
            returnHome();
        });

        //gets intent and the previous high score.
        Intent oldValue = getIntent();
        String value = oldValue.getStringExtra("currentScore");
        pushToFront("Score: "+value,"quiz0_quiz_result_textid");

        //GET THE OTHER ONE, SEE IF WE NEED TO WRITE A NEW HIGH SCORE
        String yesValWrite = oldValue.getStringExtra("yesWriteNeeded");// check if it null
        String noValWrite = oldValue.getStringExtra("noWriteNeeded");


        //CHANGE HERE, QUIZ 0 IS O THUS
        int indexToChange = 0;//at this spot we change it
        if(yesValWrite != null)
        {
            //we must write the new value in

            BufferedReader bf = makeBufferedReader("quiz_high_scores_inorder.txt");
            ArrayList<String> scores = listOfScores(bf);
            writeScores(scores,indexToChange,yesValWrite);



        }

    }


    /*
     * Author: Eder Martinez
     * Purpose: to push to front the text at the given location either button or it does not matter
     * since we can id it by string
     *
     */
    private void pushToFront(String text, String location)
    {
        try
        {
            int id = getResources().getIdentifier(location,"id",getPackageName());

            //where we are going to set the text
            TextView tv = findViewById(id);

            tv.setText(text);
        }catch (NullPointerException ext)
        {
            makeAlertStop("Error: PushToFrontException A_0_BG_Menu_Q");
        }

    }


    /*
     * Author: Eder Martinez
     * Purpose: Returns the user home when an error occurs
     * */
    private void returnHome()
    {
        Intent home = new Intent(this, MainMenu.class);
        startActivity(home);
    }


    private ArrayList<String> listOfScores(BufferedReader bf)
    {
        ArrayList<String> list = new ArrayList<>();
        try
        {

            String line = bf.readLine();
            while (line != null)
            {

                list.add(line);
                line = bf.readLine();
            }
        }
        catch (IOException e)
        {
            makeAlertStop("Error: listOfScores A_0_BG_Menu_Q: Error reading in");
        }

        return list;
    }

    private void writeScores(ArrayList<String> list, int posToChange, String highScore)
    {
        //use file and filewriter to make sure its deleted
        list.set(posToChange, highScore);
        File fileWriteTo = new File("quiz_high_scores_inorder.txt");

        if(fileWriteTo.exists())//file here import file
        {
            fileWriteTo.delete();
        }

        //create new buffered reader

        try{
            BufferedWriter write = new BufferedWriter(new FileWriter(fileWriteTo));

            for(String score: list)
            {
                write.write(score);
                write.newLine();//write a newline after
            }


        }
        catch(IOException except)
        {
            File internalStorage = getFilesDir();

            makeAlertStop(internalStorage.getAbsolutePath());
        }



    }


    private BufferedReader makeBufferedReader(String fileName)
    {
        try {
            //turn string into int and get id
            int id = getResources().getIdentifier(fileName, "raw", getPackageName());
            if (id == 0)
            {
                makeAlertStop("Error: makeBufferedReader A_0_BG_Menu_Q");
            }

            //get raw resource (see documentation)
            InputStream is = getResources().openRawResource(id);
            //add to a buffer
            BufferedReader bf = new BufferedReader(new InputStreamReader((is)));

            //buffered reader return
            return bf;
        }
        catch (Exception e)
        {
            makeAlertStop("Error: getBufferedreaderException A_0_BG_Menu_Q");
        }
        //we return a default value
        return new BufferedReader(new StringReader("Error in reader"));

    }

    /*
     * Author: Eder Martinez
     * Purpose: to make alerts and push them up when needed
     *
     * */
    private void makeAlertStop(String id)
    {
        new AlertDialog.Builder(this)
                .setTitle(id)
                .setMessage("Returning to main menu. Notify developer if possible")
                .setPositiveButton("Okay",(dialog,which)->
                {
                    dialog.dismiss();
                    returnHome();
                }).show();

    }

}