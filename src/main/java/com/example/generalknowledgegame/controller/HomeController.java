package com.example.generalknowledgegame.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.generalknowledgegame.data.HelperMethods;
import com.example.generalknowledgegame.view.AboutScreen;
import com.example.generalknowledgegame.view.DifficultySettingScreen;
import com.example.generalknowledgegame.view.InstructionsScreen;
import com.example.generalknowledgegame.view.RecordsScreen;
import com.example.generalknowledgegame.view.SettingsScreen;

public class HomeController {

    private Context context;
    private ImageButton btnSettings;
    private Button btnStart, btnInstructions, btnRecords, btnAbout;

    private boolean switchingScreen = false;

    public HomeController(Context c, ImageButton A, Button B, Button C, Button D, Button E) {
        this.context = c;
        this.btnSettings = A;
        this.btnStart = B;
        this.btnInstructions = C;
        this.btnRecords = D;
        this.btnAbout = E;
    }

    public void initialiseHome() {
        startGameButton(btnStart);
        viewInstructions(btnInstructions);
        viewRecords(btnRecords);
        viewAbout(btnAbout);
        viewSettings(btnSettings);
    }

    private void startGameButton(final Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethods.animateButton(btn);
                startGame();
            }
        });
    }

    private void viewInstructions(final Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethods.animateButton(btn);
                openInstructionsScreen();
            }
        });
    }

    private void viewRecords(final Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethods.animateButton(btn);
                openRecordsScreen();
            }
        });
    }

    private void viewAbout(final Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethods.animateButton(btn);
                openAboutScreen();
            }
        });
    }

    private void viewSettings(final ImageButton btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethods.animateImageButton(btn);
                openSettingsScreen();
            }
        });
    }

    public void onBackPressed() {
        ((AppCompatActivity) context).finishAffinity();
        System.exit(0);
    }

    private void startGame() {
        changeScreen(DifficultySettingScreen.class);
        switchingScreen = true;
    }

    private void openInstructionsScreen() {
        changeScreen(InstructionsScreen.class);
        switchingScreen = true;
    }

    private void openRecordsScreen() {
        changeScreen(RecordsScreen.class);
        switchingScreen = true;
    }

    private void openAboutScreen() {
        changeScreen(AboutScreen.class);
        switchingScreen = true;
    }

    private void openSettingsScreen() {
        changeScreen(SettingsScreen.class);
        switchingScreen = true;
    }

    private void changeScreen(Class c) {
        Intent intent = new Intent(context.getApplicationContext(), c);
        context.startActivity(intent);
        HelperMethods.activityTransitionForward(context);
        HelperMethods.disableActivity(context);
    }

    public boolean isSwitchingScreen() {
        return switchingScreen;
    }

}
