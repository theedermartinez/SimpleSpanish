/*
* Author: Eder Martinez
* Purpose: to host the first lesson of the app. We will choose here from two. either lesson or quiz
*
*
 */
package com.example.simplespanish;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class A_0_BasicGreetings_menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.a_0_basic_greetings_menu);
        //to deal with legacy displays
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.a_0_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //lesson button
        Button lesson = findViewById(R.id.a_0_menu_lesson_buttonid);
        lesson.setOnClickListener(v ->
        {
            Intent go = new Intent(A_0_BasicGreetings_menu.this, A_0_BasicGreetings_menu_lesson.class);
            startActivity(go);
        });

        //quiz button

        Button quiz = findViewById(R.id.a_0_menu_quiz_buttonid);
        quiz.setOnClickListener(v ->
        {
            Intent go = new Intent(A_0_BasicGreetings_menu.this, A_0_BasicGreetings_menu_quiz.class);
            startActivity(go);
        });
    }

}