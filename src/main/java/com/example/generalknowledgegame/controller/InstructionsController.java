package com.example.generalknowledgegame.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.generalknowledgegame.R;
import com.example.generalknowledgegame.data.HelperMethods;
import com.example.generalknowledgegame.view.DifficultySettingScreen;
import com.example.generalknowledgegame.view.HomeScreen;

public class InstructionsController {

    private Context context;
    private ImageButton btnHome;
    private Button btnStart;

    private boolean switchingScreen = false;

    public InstructionsController(Context c, ImageButton A, Button C) {
        this.context = c;
        this.btnHome = A;
        this.btnStart = C;
    }

    public void initialiseInstructions() {
        homeButton(btnHome);
        startButton(btnStart);
    }

    public void onBackPressed() {
        openHomeScreen();
    }

    private void startButton(final Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethods.animateButton(btn);
                startGame();
            }
        });
    }

    private void homeButton(final ImageButton btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethods.animateImageButton(btn);
                openHomeScreen();
            }
        });
    }

    private void startGame() {
        changeScreen(DifficultySettingScreen.class, R.anim.activity_entry_animation, R.anim.activity_exit_animation);
    }

    private void openHomeScreen() {
        changeScreen(HomeScreen.class, R.anim.activity_entry_animation_2, R.anim.activity_exit_animation_2);
    }

    private void changeScreen(Class c, int entry, int exit) {
        Intent intent = new Intent(context.getApplicationContext(), c);
        context.startActivity(intent);
        switchingScreen = true;
        ((AppCompatActivity) context).overridePendingTransition(entry, exit);
        HelperMethods.disableActivity(context);
    }

    public boolean isSwitchingScreen() {
        return switchingScreen;
    }

}
