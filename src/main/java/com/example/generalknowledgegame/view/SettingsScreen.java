package com.example.generalknowledgegame.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.generalknowledgegame.R;
import com.example.generalknowledgegame.controller.SettingsController;
import com.example.generalknowledgegame.data.HelperMethods;
import com.example.generalknowledgegame.data.MenuSoundService;

import java.util.Objects;

public class SettingsScreen extends AppCompatActivity {

    private SettingsController sc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HelperMethods.loadingActivity(this, 500);
        HelperMethods.setTheme(this);
        HelperMethods.setCorrectView(this, R.layout.activity_settings_screen_small, R.layout.activity_settings_screen_small_v2, R.layout.activity_settings_screen);

        Objects.requireNonNull(getSupportActionBar()).hide();

        ImageButton btnHome = findViewById(R.id.btnHome);

        Button theme1Selector = findViewById(R.id.btnTheme1);
        Button theme2Selector = findViewById(R.id.btnTheme2);

        SharedPreferences themeStorage = getSharedPreferences("Themes", 0);

        if (themeStorage.getString("theme", "").isEmpty()) {
            theme1Selector.setText(R.string.theme1ButtonText2);
            theme1Selector.setTextColor(Color.parseColor("#FFFF00"));
            theme1Selector.setEnabled(false);
        }

        ImageView theme1Selected = findViewById(R.id.imgTheme1Selected);
        ImageView theme2Selected = findViewById(R.id.imgTheme2Selected);

        sc = new SettingsController(this, btnHome, theme1Selector, theme2Selector, theme1Selected, theme2Selected);
        sc.initialiseSettings();
    }

    @Override
    public void onBackPressed() {
        sc.onBackPressed();
    }

    @Override
    public void onPause() {
        if (!isFinishing()) {
            if (!sc.isSwitchingScreen()) {
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
