package com.example.generalknowledgegame.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.generalknowledgegame.R;
import com.example.generalknowledgegame.controller.InstructionsController;
import com.example.generalknowledgegame.data.HelperMethods;
import com.example.generalknowledgegame.data.MenuSoundService;

import java.util.Objects;

public class InstructionsScreen extends AppCompatActivity {

    private InstructionsController ic;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HelperMethods.loadingActivity(this, 500);
        HelperMethods.setTheme(this);
        HelperMethods.setCorrectView(this, R.layout.activity_instructions_screen_small, R.layout.activity_instructions_screen_small_v2, R.layout.activity_instructions_screen);

        Objects.requireNonNull(getSupportActionBar()).hide();

        TextView tvInstructions = findViewById(R.id.tvInstructions2);
        tvInstructions.setText("\n\nThis is a simple and fun game where you are challenged to answer a set of questions correctly."
                + "\n\nGetting a question correct means that you will get a point, and if you get the wrong answer then you will score no points for that question."
                + "\n\nThe questions will be chosen at random, meaning that no two sets of questions for two different games will be exactly the same."
                + "\n\nThe questions will be quite challenging, meaning that you will have to be extremely knowledgeable to be able to get a full score."
                + "\n\nThere will always be 4 options to select for each question, clicking on the answer button will allow you to answer the question.\n\n"
        );

        ImageButton btnHome = findViewById(R.id.btnHome);
        Button btnStartGame = findViewById(R.id.btnStart);

        ic = new InstructionsController(this, btnHome, btnStartGame);
        ic.initialiseInstructions();
    }

    @Override
    public void onBackPressed() {
        ic.onBackPressed();
    }

    @Override
    public void onPause() {
        if (!isFinishing()) {
            if (!ic.isSwitchingScreen()) {
                HelperMethods.stopService(this, MenuSoundService.class);
                MenuSoundService.notFirstStart();
                MenuSoundService.setSwitchingScreens(false);
            } else {
                MenuSoundService.setSwitchingScreens(true);
            }
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        HelperMethods.startService(this, MenuSoundService.class);
        super.onResume();
    }

}
