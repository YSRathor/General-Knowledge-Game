package com.example.generalknowledgegame.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.generalknowledgegame.R;
import com.example.generalknowledgegame.controller.GameOverController;
import com.example.generalknowledgegame.data.HelperMethods;
import com.example.generalknowledgegame.data.MenuSoundService;

import java.util.Objects;

public class GameOverScreen extends AppCompatActivity {

    private GameOverController goc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HelperMethods.loadingActivity(this, 500);
        HelperMethods.setTheme(this);
        HelperMethods.setCorrectView(this, R.layout.activity_game_over_screen_small, R.layout.activity_game_over_screen_small_v2, R.layout.activity_game_over_screen);

        Objects.requireNonNull(getSupportActionBar()).hide();

        TextView tvHighScore = findViewById(R.id.tvHighScore);

        ImageButton btnHome = findViewById(R.id.btnHome);
        TextView tvFinalScore = findViewById(R.id.tvFinalScore);

        TextView tvMessage = findViewById(R.id.tvMessage);
        tvMessage.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        Button btnStartAgain = findViewById(R.id.btnPlayAgain);
        Button btnChangeDifficulty = findViewById(R.id.btnChangeDifficulty);
        Button btnExitApplication = findViewById(R.id.btnExitApplication);

        int finalScore = Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra("score")));
        int fullScore = Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra("questions")));
        int scorePercentage = (int) Math.round(((double) finalScore / (double) fullScore) * 100);

        String s = getIntent().getStringExtra("score");
        tvFinalScore.setText(s);

        goc = new GameOverController(this, finalScore, fullScore, scorePercentage, tvHighScore, tvMessage, btnHome, btnStartAgain, btnChangeDifficulty, btnExitApplication);
        goc.displayGameOver();
    }

    @Override
    public void onBackPressed() {
        goc.returnHome();
    }

    @Override
    public void onPause() {
        if (!isFinishing()) {
            if (!goc.isSwitchingScreen()) {
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
