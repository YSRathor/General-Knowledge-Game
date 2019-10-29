package com.example.generalknowledgegame.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.generalknowledgegame.R;
import com.example.generalknowledgegame.controller.AboutController;
import com.example.generalknowledgegame.data.HelperMethods;
import com.example.generalknowledgegame.data.MenuSoundService;

import java.util.Objects;

public class AboutScreen extends AppCompatActivity {

    private AboutController ac;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HelperMethods.loadingActivity(this, 500);
        HelperMethods.setTheme(this);
        HelperMethods.setCorrectView(this, R.layout.activity_about_screen_small, R.layout.activity_about_screen_small_v2, R.layout.activity_about_screen);

        Objects.requireNonNull(getSupportActionBar()).hide();

        ImageButton btnHome = findViewById(R.id.btnHome);

        TextView tvAbout = findViewById(R.id.tvAbout2);
        tvAbout.setText("\n\nThis application is the first ever project that I have developed by scratch inside Android Studio."
                + "\n\nIt was quite challenging at first to gain an understanding of how app development works in Android, however thanks to my fundamental knowledge"
                + "of the Java programming language, I was able to learn the basics relatively quickly."
                + "\n\nMany of the concepts surrounding mobile app development are similar to desktop software development, since both warrant a good understanding of "
                + "user experience (UX) and user interface (UI) designs."
                + "\n\nThis mobile application is inspired by many quiz TV shows that have a multiple choice questions format, such as Who Wants To Be A Millionaire, " +
                "The Chase and 1 VS 100, etc.\n\n");

        ac = new AboutController(this, btnHome);
        ac.initialiseAbout();
    }

    @Override
    public void onBackPressed() {
        ac.onBackPressed();
    }

    @Override
    public void onPause() {
        if (!isFinishing()) {
            if (!ac.isSwitchingScreen()) {
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
