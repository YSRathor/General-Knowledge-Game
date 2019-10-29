package com.example.generalknowledgegame.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.generalknowledgegame.R;
import com.example.generalknowledgegame.controller.GameController;
import com.example.generalknowledgegame.data.GameSoundService;
import com.example.generalknowledgegame.data.HelperMethods;
import com.example.generalknowledgegame.data.ImportQuestions;
import com.example.generalknowledgegame.model.QuestionBank;

import java.util.Objects;

public class GameScreen extends AppCompatActivity {

    private GameController gc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HelperMethods.loadingActivity(this, 500);
        HelperMethods.setTheme(this);
        HelperMethods.setCorrectView(this, R.layout.activity_game_screen_small, R.layout.activity_game_screen_small_v2, R.layout.activity_game_screen);

        Objects.requireNonNull(getSupportActionBar()).hide();

        QuestionBank model = new QuestionBank();
        ImportQuestions imQ = new ImportQuestions();

        TextView tvGameTitle = findViewById(R.id.lblGameTitle);

        TextView tvQuestionText = findViewById(R.id.tvQuestionText);
        tvQuestionText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        ImageButton btnHome = findViewById(R.id.btnHome);
        ImageButton btnPause = findViewById(R.id.btnPause);
        Button btnA = findViewById(R.id.btnA);
        Button btnB = findViewById(R.id.btnB);
        Button btnC = findViewById(R.id.btnC);
        Button btnD = findViewById(R.id.btnD);
        TextView tvDifficultySetting = findViewById(R.id.tvDifficultySetting);
        TextView tvScore = findViewById(R.id.tvScore);
        TextView tvBestScore = findViewById(R.id.tvBestScore);

        imQ.loadData(model, this);

        model.removeDuplicates(model.getQuestions());

        int n = Integer.valueOf(Objects.requireNonNull(getIntent().getStringExtra("difficulty")));
        model.filterQuestions(n);

        gc = new GameController(this, imQ, n, model, tvGameTitle, tvQuestionText, btnHome, btnPause, btnA, btnB, btnC, btnD, tvDifficultySetting, tvScore, tvBestScore);
        gc.setupGame();
    }

    @Override
    public void onBackPressed() {
        gc.onBackPressed();
    }

    @Override
    public void onPause() {
        if (!isFinishing()) {
            if (!gc.isSwitchingScreen()) {
                HelperMethods.stopService(this, GameSoundService.class);
                GameSoundService.notFirstStart();
                GameSoundService.setSwitchingScreens(false);
            } else {
                HelperMethods.stopService(this, GameSoundService.class);
                GameSoundService.setSwitchingScreens(true);
            }
        }
        gc.onPause();
        gc.setAppPaused(true);
        super.onPause();
    }

    @Override
    public void onResume() {
        HelperMethods.startService(this, GameSoundService.class);
        gc.setAppPaused(false);
        gc.onResume();
        super.onResume();
    }

}
