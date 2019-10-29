package com.example.generalknowledgegame.view;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.generalknowledgegame.R;
import com.example.generalknowledgegame.controller.SplashScreenController;
import com.example.generalknowledgegame.data.HelperMethods;

import java.util.Objects;

public class SplashScreen extends AppCompatActivity {

    private SplashScreenController ssc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HelperMethods.setCorrectView(this, R.layout.activity_splash_screen_small, R.layout.activity_splash_screen_small_v2, R.layout.activity_splash_screen);

        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ConstraintLayout cl = findViewById(R.id.splash_screen_layout);
        AnimationDrawable ad = (AnimationDrawable) cl.getBackground();
        ad.setEnterFadeDuration(2000);
        ad.setExitFadeDuration(4000);
        ad.start();

        TextView tvPercentageLabel = findViewById(R.id.tvPercentageLabel);
        TextView tvLoadingPercentage = findViewById(R.id.tvLoadingPercentage);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        Button btnContinue = findViewById(R.id.btnContinue);

        ssc = new SplashScreenController(this, tvPercentageLabel, tvLoadingPercentage, progressBar, btnContinue);
        ssc.initialiseSplashScreen();
    }

    @Override
    public void onBackPressed() {
        ssc.onBackPressed();
    }

}
