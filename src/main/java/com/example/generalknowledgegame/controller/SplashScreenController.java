package com.example.generalknowledgegame.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.generalknowledgegame.data.HelperMethods;
import com.example.generalknowledgegame.data.ProgressBarAnimation;
import com.example.generalknowledgegame.view.HomeScreen;

public class SplashScreenController {

    private Context context;
    private TextView tvPercentageLabel;
    private ProgressBar progressBar;
    private TextView tvLoadingPercentage;
    private Button btnContinue;

    public SplashScreenController(Context c, TextView tvP, TextView tvL, ProgressBar pb, Button btn) {
        this.context = c;
        this.tvPercentageLabel = tvP;
        this.tvLoadingPercentage = tvL;
        this.progressBar = pb;
        this.btnContinue = btn;
    }

    public void initialiseSplashScreen() {
        setupProgressBar();
        animateProgressBar();
        continueButton(btnContinue);
    }

    private void setupProgressBar() {
        progressBar.setMax(100);
        progressBar.setScaleY(3f);
    }

    private void animateProgressBar() {
        ProgressBarAnimation anim = new ProgressBarAnimation(context, tvPercentageLabel, tvLoadingPercentage, progressBar, 0f, 100f, btnContinue);
        anim.setDuration(5000);
        progressBar.setAnimation(anim);
    }

    private void continueButton(final Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethods.animateButton(btn);
                goHome();
            }
        });
    }

    public void onBackPressed() {
        ((AppCompatActivity) context).finishAffinity();
        System.exit(0);
    }

    private void goHome() {
        Intent intent = new Intent(context.getApplicationContext(), HomeScreen.class);
        context.startActivity(intent);
        HelperMethods.disableActivity(context);
    }

}
