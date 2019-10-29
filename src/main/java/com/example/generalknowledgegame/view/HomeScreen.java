package com.example.generalknowledgegame.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.generalknowledgegame.R;
import com.example.generalknowledgegame.controller.HomeController;
import com.example.generalknowledgegame.data.HelperMethods;
import com.example.generalknowledgegame.data.MenuSoundService;

import java.util.Objects;

public class HomeScreen extends AppCompatActivity {

    private HomeController hc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HelperMethods.loadingActivity(this, 500);
        HelperMethods.setTheme(this);
        HelperMethods.setCorrectView(this, R.layout.activity_home_screen_small, R.layout.activity_home_screen_small_v2, R.layout.activity_home_screen);

        Objects.requireNonNull(getSupportActionBar()).hide();

        Button btnStartGame = findViewById(R.id.btnStart);
        Button btnInstructions = findViewById(R.id.btnInstructions);
        Button btnRecords = findViewById(R.id.btnRecords);
        Button btnAbout = findViewById(R.id.btnAbout);

        ImageButton btnSettings = findViewById(R.id.btnSettings);

        hc = new HomeController(this, btnSettings, btnStartGame, btnInstructions, btnRecords, btnAbout);
        hc.initialiseHome();
    }

    @Override
    public void onBackPressed() {
        hc.onBackPressed();
    }

    @Override
    public void onPause() {
        if (!isFinishing()) {
            if (!hc.isSwitchingScreen()) {
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
