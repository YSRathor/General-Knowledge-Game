package com.example.generalknowledgegame.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.generalknowledgegame.R;
import com.example.generalknowledgegame.controller.DifficultySettingController;
import com.example.generalknowledgegame.data.HelperMethods;
import com.example.generalknowledgegame.data.MenuSoundService;

import java.util.Objects;

public class DifficultySettingScreen extends AppCompatActivity {

    private DifficultySettingController dsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HelperMethods.loadingActivity(this, 500);
        HelperMethods.setTheme(this);
        HelperMethods.setCorrectView(this, R.layout.activity_difficulty_setting_screen_small,
                R.layout.activity_difficulty_setting_screen_small_v2, R.layout.activity_difficulty_setting_screen);

        Objects.requireNonNull(getSupportActionBar()).hide();

        ImageButton btnHome = findViewById(R.id.btnHome);
        Button btnEasy = findViewById(R.id.btnEasy);
        Button btnMedium = findViewById(R.id.btnMedium);
        Button btnHard = findViewById(R.id.btnHard);
        Button btnVeryHard = findViewById(R.id.btnVeryHard);

        btnEasy.setText(HelperMethods.customFontSizes("Easy" + "\n(10 Questions)", 0.5f, 4, 19));
        btnMedium.setText(HelperMethods.customFontSizes("Medium" + "\n(20 Questions)", 0.5f, 6, 21));
        btnHard.setText(HelperMethods.customFontSizes("Hard" + "\n(50 Questions)", 0.5f, 4, 19));
        btnVeryHard.setText(HelperMethods.customFontSizes("Very Hard" + "\n(100 Questions)", 0.5f, 9, 25));

        dsc = new DifficultySettingController(this, btnHome, btnEasy, btnMedium, btnHard, btnVeryHard);
        dsc.setDifficulty();
    }

    @Override
    public void onBackPressed() {
        dsc.onBackPressed();
    }

    @Override
    public void onPause() {
        if (!isFinishing()) {
            if (dsc.isSwitchingToGameScreen()) {
                HelperMethods.stopService(this, MenuSoundService.class);
                MenuSoundService.setSwitchingScreens(true);
            } else if (dsc.isSwitchingToHomeScreen()) {
                MenuSoundService.setSwitchingScreens(true);
            } else {
                HelperMethods.stopService(this, MenuSoundService.class);
                MenuSoundService.notFirstStart();
                MenuSoundService.setSwitchingScreens(false);
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
