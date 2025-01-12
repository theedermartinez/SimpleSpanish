/*
* Author: Eder Martinez
* Purpose: to hold the quiz. It will get the info from the text file.
* Comments: We initalize the buttons and then we pass them in. We then set up a listener for each individual button. When it is pressed, we check
*
 */

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

//to use array list
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class A_0_BasicGreetings_menu_quiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for old displays
        EdgeToEdge.enable(this);
        setContentView(R.layout.a_0_basic_greetings_menu_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.a_0_main_quiz), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // import full file and put it in a  array one per question to keep track, create a arraylist to keep track of the questions left
        //initialte the buttons
        Button button0 = findViewById(R.id.quiz0_answer0_buttonid);
        Button button1 = findViewById(R.id.quiz0_answer1_buttonid);
        Button button2 = findViewById(R.id.quiz0_answer2_buttonid);
        Button button3 = findViewById(R.id.quiz0_answer3_buttonid);

        //make high score and push it
        BufferedReader bf = makeBufferedReader("quiz_high_scores_inorder");
        try
        {
            String highScore = "High Score:"+bf.readLine();
            pushToFront(highScore,"quiz0_maxscore_textid");
        }
        catch (Exception e)
        {
            makeAlertStop("Error pushing score");
        }
        //make quiz
        readFileQuiz("a_0_basic_greetings_quiz",button0,button1,button2,button3);




    }

    /*
     * Author: Eder Martinez
     * Purpose: based on read file. We will read one line at a time. slowsly and then update the score.
     *
     * */
    private void readFileQuiz(String filename, Button button0, Button button1, Button button2, Button button3)
    {

        try
        {
            //turn string into int and get id using new method
            BufferedReader bf = makeBufferedReader(filename);

            //variables used in the loop below
            //add to arraylist so we can determine the size dynamically first and then to 2d array once we know the size
            ArrayList<String> keepQuestionsArrayList = new ArrayList<>();
            boolean endOfFile = false;
            //start here loop
            StringBuilder sb = new StringBuilder();
            sb.append(bf.readLine());

            while(endOfFile == false)
            {
                keepQuestionsArrayList.add(sb.toString());
                sb.setLength(0);//to clear it
                //now add the next row
                String nextL = bf.readLine();
                if(nextL == null)
                {
                    endOfFile = true;
                    break;
                }
                sb.append(nextL);//next line to add

            }
            // here everything is added to the arraylist. Now we create a 2d array with [keepuestion.length][6] // quesitons,1,2,3,4,answer
            //quiz length is detemrined by the length of the arraylist when all the questions are in it
            int quizLength = keepQuestionsArrayList.size();

            String[][] keep2dArray = new String[keepQuestionsArrayList.size()][6];//making it the right size

            //now we grab each line from the arraylist, split using comas and add it to the 2d array

            int keepQuestionsAddIndex = 0;// to use within the loop

            while(!keepQuestionsArrayList.isEmpty())
            {

                //check if length is correct

                String quest = keepQuestionsArrayList.get(0);
                String[] splitArray = quest.split(",");
                if(splitArray.length != 6)
                {
                    makeAlertStop("Error: readFileQuiz A_0_BG_menu_L: Format Wrong");
                }

                //add every element of the split array
                for(int i = 0; i < splitArray.length; i++)
                {
                    keep2dArray[keepQuestionsAddIndex][i] = splitArray[i];
                }

                keepQuestionsArrayList.remove(0);// remove the index
                keepQuestionsAddIndex++;//increase the row


            }

            //after added we display it

            //following variables are used for moving and answering
            //moved to method displayFileQuiz

            displayFileQuiz(keep2dArray,button0,button1,button2,button3,quizLength);


        }
        catch (IOException e)
        {
            makeAlertStop("Error: readFileException A_0_BG_menu_L");

        }
    }


    /*Author: Eder Martinez
    * Purpose: Helper method of readFilequiz. It will display and stay here until quiz is over each button is paseed in and listened to
    * Notes: Read about atomic integers and how the allow change
    * DO NOT FORGET TO UPDATE WITHIN THE LISTENER TO CHANGE THE OUTCOME OF WHAT IS DISPLAYD
    *
     */
    private void displayFileQuiz(String[][] array, Button button0, Button button1, Button button2, Button button3,int maxLength)
    {
        AtomicInteger currentQuestionIndexRow = new AtomicInteger(0);//used to move the 2d array
        boolean endQuiz = false;


            /*
            old can be deleted
            pushToFront(array[currentQuestionIndexRow.get()][0], "quiz0_question_textid");//question
            pushToFront(array[currentQuestionIndexRow.get()][1], "quiz0_answer0_buttonid");//opt 0
            pushToFront(array[currentQuestionIndexRow.get()][2], "quiz0_answer1_buttonid");// opt 1
            pushToFront(array[currentQuestionIndexRow.get()][3], "quiz0_answer2_buttonid");// opt 2
            pushToFront(array[currentQuestionIndexRow.get()][4], "quiz0_answer3_buttonid");// opt 3
            */

        updateEveryButton(array,currentQuestionIndexRow);
        AtomicInteger numToAddScore = new AtomicInteger(0);
        AtomicInteger currentScore = new AtomicInteger(0);// we use atomic to keep it updating and it does not return to 0

        //button listeners

        button0.setOnClickListener(v->
        {
            numToAddScore.getAndAdd(checkCorrectAnswer(array[currentQuestionIndexRow.get()][5],array[currentQuestionIndexRow.get()][1]));
            currentQuestionIndexRow.incrementAndGet();
            checkIfEnd(currentQuestionIndexRow.get(),maxLength,currentScore.get());
            updateEveryButton(array,currentQuestionIndexRow);
            updateCurrScore(numToAddScore,currentScore);


        });

        button1.setOnClickListener(v->
        {
            numToAddScore.getAndAdd(checkCorrectAnswer(array[currentQuestionIndexRow.get()][5],array[currentQuestionIndexRow.get()][2]));
            currentQuestionIndexRow.incrementAndGet();
            checkIfEnd(currentQuestionIndexRow.get(),maxLength,currentScore.get());
            updateEveryButton(array,currentQuestionIndexRow);
            updateCurrScore(numToAddScore,currentScore);


        });

        button2.setOnClickListener(v->
        {
            numToAddScore.getAndAdd(checkCorrectAnswer(array[currentQuestionIndexRow.get()][5],array[currentQuestionIndexRow.get()][3]));
            currentQuestionIndexRow.incrementAndGet();
            checkIfEnd(currentQuestionIndexRow.get(),maxLength,currentScore.get());
            updateEveryButton(array,currentQuestionIndexRow);
            updateCurrScore(numToAddScore,currentScore);

        });

        button3.setOnClickListener(v->
        {
            numToAddScore.getAndAdd(checkCorrectAnswer(array[currentQuestionIndexRow.get()][5],array[currentQuestionIndexRow.get()][4]));
            currentQuestionIndexRow.incrementAndGet();
            checkIfEnd(currentQuestionIndexRow.get(),maxLength,currentScore.get());
            updateEveryButton(array,currentQuestionIndexRow);
            updateCurrScore(numToAddScore,currentScore);

        });


        updateCurrScore(numToAddScore,currentScore);

    }


    /*Author: Eder Martinez
    * Purpose: update the displayFileQuiz, leading to updates when chaning button
    * **/
    private void updateEveryButton(String[][] array,AtomicInteger currentQuestionIndexRow)
    {
        pushToFront(array[currentQuestionIndexRow.get()][0], "quiz0_question_textid");//question
        pushToFront(array[currentQuestionIndexRow.get()][1], "quiz0_answer0_buttonid");//opt 0
        pushToFront(array[currentQuestionIndexRow.get()][2], "quiz0_answer1_buttonid");// opt 1
        pushToFront(array[currentQuestionIndexRow.get()][3], "quiz0_answer2_buttonid");// opt 2
        pushToFront(array[currentQuestionIndexRow.get()][4], "quiz0_answer3_buttonid");// opt 3
    }

    /**
     * Author: Eder Martinez
     * Purpose: solely to update score
     *
     *
     * */
    private void updateCurrScore(AtomicInteger addO, AtomicInteger currScore)
    {
        currScore.getAndAdd(addO.get());
        String score = Integer.toString(currScore.get());
        String pushThis = "Current Score:" +score;
        pushToFront(pushThis,"quiz0_curscore_textid");
    }

    /*
    * Author: Eder Martinez
    * Purpose: check if it is the end. Current represents the current row, end is the final and currentScore is the one we will push
    * at the end
    *
    * */
    private void checkIfEnd(int current, int end, int currentScore)
    {
        if(current==end)
        {
            Intent go = new Intent(this, A_0_BasicGreetings_menu_quiz_zend.class);
            String curScoreString = Integer.toString(current);
            go.putExtra( "currentScore",curScoreString);
            startActivity(go);
            finish();//use carefully
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




    /*within we have to push and read from the file
    * Author: Eder Martinez
    * Purpose: To return a buffered stream from a given file within raw in the res folder
    *
    *  */
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
    * purpose: Check if this is the corrct answer, if so it will return a 1 to add to the score otherwise, it will
    * add 0 keeping the score the same
    *
    *
    * */
    private int checkCorrectAnswer(String correct, String check)
    {
        if(correct.equals(check))
        {
            return 1;
        }
        else
        {
            return 0;
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