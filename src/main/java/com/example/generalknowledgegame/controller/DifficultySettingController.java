package com.example.generalknowledgegame.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.generalknowledgegame.data.HelperMethods;
import com.example.generalknowledgegame.view.GameScreen;
import com.example.generalknowledgegame.view.HomeScreen;

public class DifficultySettingController {

    private Context context;
    private ImageButton btnHome;
    private Button btnEasy, btnMedium, btnHard, btnVeryHard;

    private boolean switchingToHomeScreen = false;
    private boolean switchingToGameScreen = false;

    public DifficultySettingController(Context c, ImageButton A, Button C, Button D, Button E, Button F) {
        this.context = c;
        this.btnHome = A;
        this.btnEasy = C;
        this.btnMedium = D;
        this.btnHard = E;
        this.btnVeryHard = F;
    }

    public void setDifficulty() {
        homeButton(btnHome);
        easySetting(btnEasy);
        mediumSetting(btnMedium);
        hardSetting(btnHard);
        veryHardSetting(btnVeryHard);
    }

    public void onBackPressed() {
        goHome();
    }

    private void homeButton(final ImageButton btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethods.animateImageButton(btn);
                goHome();
            }
        });
    }

    private void easySetting(final Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethods.animateButton(btn);
                startGame(10);
            }
        });
    }

    private void mediumSetting(final Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethods.animateButton(btn);
                startGame(20);
            }
        });
    }

    private void hardSetting(final Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethods.animateButton(btn);
                startGame(50);
            }
        });
    }

    private void veryHardSetting(final Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethods.animateButton(btn);
                startGame(100);
            }
        });
    }

    private void startGame(int n) {
        Intent intent = new Intent(context.getApplicationContext(), GameScreen.class);
        intent.putExtra("difficulty", String.valueOf(n));
        context.startActivity(intent);
        switchingToGameScreen = true;
        HelperMethods.activityTransitionForward(context);
        HelperMethods.disableActivity(context);
    }

    private void goHome() {
        Intent intent = new Intent(context.getApplicationContext(), HomeScreen.class);
        context.startActivity(intent);
        switchingToHomeScreen = true;
        HelperMethods.activityTransitionBack(context);
        HelperMethods.disableActivity(context);
    }

    public boolean isSwitchingToHomeScreen() {
        return switchingToHomeScreen;
    }

    public boolean isSwitchingToGameScreen() {
        return switchingToGameScreen;
    }

}
