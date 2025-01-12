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