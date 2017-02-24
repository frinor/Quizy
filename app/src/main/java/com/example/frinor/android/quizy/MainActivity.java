package com.example.frinor.android.quizy;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    int score = 0;
    int maxScore = 17;

    int scoreRadio = 3;
    int scoreCheckbox = 1;
    int scoreEditText = 3;


    String[] answerRadioGroupIds = {"radio_group1", "radio_group4", "radio_group5", "radio_group6"};

    String[] answerRadioIds = {
            "answer1_1", "answer1_2", "answer1_3", "answer4_1", "answer4_2", "answer4_3",
            "answer5_1", "answer5_2", "answer5_3", "answer6_1", "answer6_2", "answer6_3"};

    String[] answerCheckBoxIds = { "answer2_1", "answer2_2", "answer2_3"};


    String[] answerScoreRadio = {
            "answer1_1-2", "answer1_2-2", "answer1_3-2",
            "answer4_1-2", "answer4_2-2", "answer4_3-2",
            "answer5_1-3", "answer5_2-3", "answer5_3-3",
            "answer6_1-2", "answer6_2-2", "answer6_3-2"};

    String[] answerScoreCheckbox = {"answer2_1-1", "answer2_2-0", "answer2_3-3"};


    String answer3 = "8";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    /**
     * Checks whether answer is correct in radio button answers
     */
    public void getAnswerScoreRadio() {
        int i = 1;
        for (String radio: answerScoreRadio) {
            String[] item = radio.split("-");
            boolean isAnswerGroup = (i % 3) == 0;

            int index = Integer.parseInt(item[1]);
            int resourceId = getResources().getIdentifier(item[0], "id", getPackageName());

            RadioButton radioButton = (RadioButton) findViewById(resourceId);
            boolean checked = radioButton.isChecked();


            if (checked && i == index){
                score += scoreRadio;
                radioButton.setTextColor(ContextCompat.getColor(this, R.color.colorCorrectAnswer));
            } else if (checked && i != index){
                radioButton.setTextColor(ContextCompat.getColor(this, R.color.colorWrongAnswer));
            } else {
                radioButton.setTextColor(Color.rgb(0,0,0));
            }

            if (isAnswerGroup){
                i = 1;
            } else {
                i++;
            }
        }

    }


    /**
     * Checks whether answer is correct in checkbox answers
     */
    public void getAnswerScoreCheckbox() {
        int i = 1;
        for (String checkBoxItem: answerScoreCheckbox) {
            String[] item = checkBoxItem.split("-");
            boolean isAnswerGroup = (i % 3) == 0;


            int index = Integer.parseInt(item[1]);
            int resourceId = getResources().getIdentifier(item[0], "id", getPackageName());

            CheckBox checkBox = (CheckBox) findViewById(resourceId);
            boolean checked = checkBox.isChecked();


            if (checked && i == index){
                score += scoreCheckbox;
                checkBox.setTextColor(ContextCompat.getColor(this, R.color.colorCorrectAnswer));
            } else if (checked && i != index){
                score -= scoreCheckbox;
                checkBox.setTextColor(ContextCompat.getColor(this, R.color.colorWrongAnswer));
            } else {
                checkBox.setTextColor(Color.rgb(0,0,0));
            }


            if (isAnswerGroup){
                i = 1;
            } else {
                i++;
            }
        }

    }


    /**
     * Checks whether the input text is correct
     *
     */
    public void getAnswerScoreEditText(){
        EditText answerText = (EditText) findViewById(R.id.answer3);
        String answerText3 = answerText.getText().toString();

        if (answer3.equals(answerText3)){
            score += scoreEditText;
            answerText.setTextColor(ContextCompat.getColor(this, R.color.colorCorrectAnswer));
        } else {
            answerText.setTextColor(ContextCompat.getColor(this, R.color.colorWrongAnswer));
        }
    }


    /**
     * Checks the answers and calculate score.
     */
    public void getScores(){
        getAnswerScoreRadio();
        getAnswerScoreCheckbox();
        getAnswerScoreEditText();
    }


    /**
     * Resets the form.
     * @param view
     */
    public void onReset(View view){
        score = 0;

        resetRadioButtons();
        resetCheckboxes();

        ((EditText) findViewById(R.id.answer3)).setText("");
        ((EditText) findViewById(R.id.answer3)).setTextColor(Color.rgb(0,0,0));
    }


    /**
     * Reset radio buttons and its text color to default color
     */
    public void resetRadioButtons(){
        RadioGroup rg = (RadioGroup) findViewById(R.id.radio_group1);
        rg.clearCheck();

        for (String radioGroupId: answerRadioGroupIds) {
            int resourceId = getResources().getIdentifier(radioGroupId, "id", getPackageName());
            ((RadioGroup) findViewById(resourceId)).clearCheck();
        }

        for (String radioId: answerRadioIds) {
            int resourceId = getResources().getIdentifier(radioId, "id", getPackageName());
            ((RadioButton) findViewById(resourceId)).setTextColor(Color.rgb(0,0,0));
        }
    }


    /**
     * Reset checkboxes and its text color to default color
     */
    public void resetCheckboxes(){

        for (String radioId: answerCheckBoxIds) {
            int resourceId = getResources().getIdentifier(radioId, "id", getPackageName());

            ((CheckBox) findViewById(resourceId)).setChecked(false);
            ((CheckBox) findViewById(resourceId)).setTextColor(Color.rgb(0,0,0));
        }
    }


    /**
     * Check answers and calculates final result of the quiz
     * @param view
     */
    public void onCheckAnswers(View view) {
        String resultText;

        getScores();

        if (score == maxScore){
            resultText = getString(R.string.max_score_text, score, maxScore);
        } else {
            resultText = getString(R.string.score_text, score, maxScore);
        }

        Toast.makeText(this, resultText, Toast.LENGTH_LONG).show();
        score = 0;
    }

}

