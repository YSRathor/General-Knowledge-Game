package com.example.generalknowledgegame.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.generalknowledgegame.R;
import com.example.generalknowledgegame.data.HelperMethods;
import com.example.generalknowledgegame.data.MenuSoundService;
import com.example.generalknowledgegame.view.DifficultySettingScreen;
import com.example.generalknowledgegame.view.GameOverScreen;
import com.example.generalknowledgegame.view.GameScreen;
import com.example.generalknowledgegame.view.HomeScreen;

public class GameOverController {

    private Context context;
    private int finalScore, fullScore;
    private double scorePercentage;
    private TextView tvHighScore, tvMessage;
    private ImageButton btnHome;
    private Button btnStartAgain, btnChangeDifficulty, btnExitApplication;

    private boolean switchingScreen = false;

    public GameOverController(Context c, int i, int j, double p, TextView tvHS, TextView tvM, ImageButton A, Button B, Button C, Button D) {
        this.context = c;
        this.finalScore = i;
        this.fullScore = j;
        this.scorePercentage = p;
        this.tvHighScore = tvHS;
        this.tvMessage = tvM;
        this.btnHome = A;
        this.btnStartAgain = B;
        this.btnChangeDifficulty = C;
        this.btnExitApplication = D;
    }

    public void displayGameOver() {
        checkForHighScore();
        outputMessage();
        storeScore();
        homeButton(btnHome);
        startAgain(btnStartAgain);
        changeMode(btnChangeDifficulty);
        exitApp(btnExitApplication);
    }

    //Add more cases
    private void outputMessage() {
        if (scorePercentage == 100) {
            tvMessage.setText(R.string.output_result_1);
        } else if (scorePercentage < 100 && scorePercentage >= 80) {
            tvMessage.setText(R.string.output_result_2);
        } else if (scorePercentage <= 79 && scorePercentage >= 60) {
            tvMessage.setText(R.string.output_result_3);
        } else if (scorePercentage <= 59 && scorePercentage >= 40) {
            tvMessage.setText(R.string.output_result_4);
        } else if (scorePercentage <= 39 && scorePercentage >= 20) {
            tvMessage.setText(R.string.output_result_5);
        } else if (scorePercentage <= 19 && scorePercentage > 0) {
            tvMessage.setText(R.string.output_result_6);
        } else if (scorePercentage == 0) {
            tvMessage.setText(R.string.output_result_7);
        }
    }

    private void homeButton(final ImageButton btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethods.animateImageButton(btn);
                returnHome();
            }
        });
    }

    private void startAgain(final Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethods.animateButton(btn);
                Intent intent = new Intent(context.getApplicationContext(), GameScreen.class);
                intent.putExtra("difficulty", String.valueOf(fullScore));
                context.startActivity(intent);
                switchingScreen = true;
                HelperMethods.activityTransitionForward(context);
                HelperMethods.disableActivity(context);
                HelperMethods.stopService(context, MenuSoundService.class);
            }
        });
    }

    private void changeMode(final Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethods.animateButton(btn);
                changeScreen(DifficultySettingScreen.class, R.anim.activity_entry_animation, R.anim.activity_exit_animation);
            }
        });
    }

    private void exitApp(final Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethods.animateButton(btn);
                exitDialog();
            }
        });
    }

    private void exitDialog() {
        AlertDialog.Builder a_builder = new AlertDialog.Builder(context);
        a_builder.setMessage("Are you sure you want to exit the application?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((GameOverScreen) context).finishAffinity();
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
        ;
        AlertDialog alert = a_builder.create();
        alert.setTitle("General Knowledge Game");
        alert.show();
    }

    private void changeScreen(Class c, int entry, int exit) {
        Intent intent = new Intent(context.getApplicationContext(), c);
        context.startActivity(intent);
        switchingScreen = true;
        ((AppCompatActivity) context).overridePendingTransition(entry, exit);
        HelperMethods.disableActivity(context);
    }

    private void checkForHighScore() {
        String s = ((AppCompatActivity) context).getIntent().getStringExtra("highScore");

        if (!Boolean.parseBoolean(s)) {
            tvHighScore.setVisibility(View.VISIBLE);
        }
    }

    private void storeScore() {
        SharedPreferences mPrefs = context.getSharedPreferences("IDValue", 0);
        SharedPreferences.Editor editor = mPrefs.edit();

        editor.putString("number", String.valueOf(fullScore));

        if (fullScore == 10) {
            editor.putString("easyScore", String.valueOf(finalScore));
        } else if (fullScore == 20) {
            editor.putString("mediumScore", String.valueOf(finalScore));
        } else if (fullScore == 50) {
            editor.putString("hardScore", String.valueOf(finalScore));
        } else if (fullScore == 100) {
            editor.putString("veryHardScore", String.valueOf(finalScore));
        }

        editor.apply();
    }

    public void returnHome() {
        changeScreen(HomeScreen.class, R.anim.activity_entry_animation_2, R.anim.activity_exit_animation_2);
    }

    public boolean isSwitchingScreen() {
        return switchingScreen;
    }

}
