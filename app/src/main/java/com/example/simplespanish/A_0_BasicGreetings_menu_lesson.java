/**
 * Author: Eder Martinez
 * Purpose: Lesson 0 of the program hi miss ella pie i love you :D
 *
 **/
package com.example.simplespanish;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//to allow for input streaming


public class A_0_BasicGreetings_menu_lesson extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.a_0_basic_greetings_menu_lesson);
        //for legacy displays
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.a_0_main_lesson), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //update title of the lesson
        pushToFront("Basic Greetings","lesson0_main_tile_0id");

        //call on readfile and then push it to the updated text
        String readFile0 = readFile("a_0_basic_greetings_lesson_pt0");
        String readFile1 = readFile("a_0_basic_greetings_lesson_pt1");
        String readFile2 = readFile("a_0_basic_greetings_lesson_pt2");
        //push
        pushToFront(readFile0,"lesson0_text0id");
        pushToFront(readFile1,"lesson0_text1id");
        pushToFront(readFile2,"lesson0_text2id");



    }

    /*
     * Author: Eder Martinez
     * Purpose: called when opening a file, using a try catch it will return a string which will contain the
     * whole text as string
     *
     * */
    private String readFile(String filename)
    {
      try
      {
          //turn string into int and get id
          int id = getResources().getIdentifier(filename,"raw",getPackageName());
          if(id ==0)
          {
              alertStop( "Error: readFile A_0_BG_Menu_L");
              return "Error: Type 0 no content found";
          }
          //get raw resource (see documentation)
          InputStream is = getResources().openRawResource(id);
          //add to a buffer
          BufferedReader bf = new BufferedReader(new InputStreamReader((is)));
          //we already checked for null thus we can assign
          String line = bf.readLine();

          StringBuilder sb = new StringBuilder();
          while(line != null)
          {
            sb.append(line);
            line = bf.readLine();//test with warning
          }

          return sb.toString();

      }
      catch (IOException e)
      {
        alertStop("Error: readFileException A_0_BG_menu_L");
        return "Error: Type 0, no content found";

      }
    }


    /*
     * Author: Eder Martinez
     * Purpose: to push to front the text at the given location
     *
     * */
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
            alertStop("Error: PushToFrontException A_0_BG_Menu_L");
        }

    }

    /*
    * Author: Eder Martinez
    * Purpose: sends an alerts and returns home when an error occurs
    * */
    private void alertStop(String id)
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

    /*
    * Author: Eder Martinez
    * Purpose: Returns the user home when an error occurs
    * */
    private void returnHome()
    {
        Intent home = new Intent(this, MainMenu.class);
        startActivity(home);
    }

}

