/*
* Author: Eder Martinez
* Purpose: The main menu of the whole doucment. Home should be here. This should be loaded promptly
* Include the legacy displays
*
*
* */

package com.example.simplespanish;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainMenu extends AppCompatActivity {
//link the on create method to link the xml
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.main_menu);
        //for legacy displays
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Button start = findViewById(R.id.basic_greetings_buttonid);
        start.setSoundEffectsEnabled(false);

        start.setOnClickListener(v ->{
            Intent go = new Intent(MainMenu.this, A_0_BasicGreetings_menu.class);
            startActivity(go);
        });

    }

}