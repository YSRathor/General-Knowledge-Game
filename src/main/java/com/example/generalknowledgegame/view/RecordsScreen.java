package com.example.generalknowledgegame.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.generalknowledgegame.R;
import com.example.generalknowledgegame.controller.RecordsController;
import com.example.generalknowledgegame.data.HelperMethods;
import com.example.generalknowledgegame.data.MenuSoundService;

import java.util.Objects;

public class RecordsScreen extends AppCompatActivity {

    private RecordsController rc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HelperMethods.loadingActivity(this, 500);
        HelperMethods.setTheme(this);
        HelperMethods.setCorrectView(this, R.layout.activity_records_screen_small, R.layout.activity_records_screen_small_v2, R.layout.activity_records_screen);

        Objects.requireNonNull(getSupportActionBar()).hide();

        ImageButton btnHome = findViewById(R.id.btnHome);
        TextView tvEasyScore = findViewById(R.id.tvEasyScore);
        TextView tvMediumScore = findViewById(R.id.tvMediumScore);
        TextView tvHardScore = findViewById(R.id.tvHardScore);
        TextView tvVeryHardScore = findViewById(R.id.tvVeryHardScore);

        Button btnReset = findViewById(R.id.btnReset);

        String easyScore = "0";
        String mediumScore = "0";
        String hardScore = "0";
        String veryHardScore = "0";

        SharedPreferences mPrefs = getSharedPreferences("IDValue", 0);
        SharedPreferences mPrefs2 = getSharedPreferences("IDValue2", 0);
        SharedPreferences.Editor editor = mPrefs2.edit();
        editor.apply();

        rc = new RecordsController(this, tvEasyScore, tvMediumScore, tvHardScore, tvVeryHardScore,
                btnHome, easyScore, mediumScore, hardScore, veryHardScore, mPrefs, mPrefs2, editor, btnReset);

        rc.displayRecords();
    }

    @Override
    public void onBackPressed() {
        rc.onBackPressed();
    }

    @Override
    public void onPause() {
        if (!isFinishing()) {
            if (!rc.isSwitchingScreen()) {
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
