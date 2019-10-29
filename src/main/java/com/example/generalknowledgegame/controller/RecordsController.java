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

import com.example.generalknowledgegame.data.HelperMethods;
import com.example.generalknowledgegame.view.HomeScreen;

public class RecordsController {

    private Context context;
    private ImageButton btnHome;
    private TextView tvEasyScore, tvMediumScore, tvHardScore, tvVeryHardScore;
    private String easyScore, mediumScore, hardScore, veryHardScore;
    private SharedPreferences mPrefs;
    private SharedPreferences mPrefs2;
    private SharedPreferences.Editor editor;
    private Button btnReset;

    private boolean switchingScreen = false;

    public RecordsController(Context c, TextView tvES, TextView tvMS, TextView tvHS, TextView tvVHS,
                             ImageButton A, String s, String s2, String s3, String s4, SharedPreferences sp, SharedPreferences sp2, SharedPreferences.Editor spE, Button B) {
        this.context = c;
        this.tvEasyScore = tvES;
        this.tvMediumScore = tvMS;
        this.tvHardScore = tvHS;
        this.tvVeryHardScore = tvVHS;
        this.btnHome = A;
        this.easyScore = s;
        this.mediumScore = s2;
        this.hardScore = s3;
        this.veryHardScore = s4;
        this.mPrefs = sp;
        this.mPrefs2 = sp2;
        this.editor = spE;
        this.btnReset = B;
    }

    public void displayRecords() {
        calculateScores();
        homeButton(btnHome);
        toggleResetButton();
        resetButton(btnReset);
    }

    public void onBackPressed() {
        goHome();
    }

    private void homeButton(final ImageButton btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethods.animateImageButton(btn);
                goHome();
            }
        });
    }

    private void resetButton(final Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethods.animateButton(btn);
                resetDialog();
            }
        });
    }

    private void resetDialog() {
        HelperMethods.disableActivity(context);
        AlertDialog.Builder a_builder = new AlertDialog.Builder(context);
        a_builder.setMessage("Are you sure want to reset your top scores?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reset();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HelperMethods.enableActivity(context);
                    }
                })
        ;
        AlertDialog alert = a_builder.create();
        alert.setTitle("General Knowledge Game");
        alert.show();
    }

    private void reset() {
        mPrefs.edit().clear().apply();
        mPrefs2.edit().clear().apply();
        AlertDialog.Builder a_builder = new AlertDialog.Builder(context);
        a_builder.setMessage("Scores Reset complete!")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //btnHome.setEnabled(false);
                        HelperMethods.reloadActivity(context);
                    }
                })
        ;
        AlertDialog alert = a_builder.create();
        alert.setTitle("General Knowledge Game");
        alert.show();
    }

    private void goHome() {
        Intent intent = new Intent(context.getApplicationContext(), HomeScreen.class);
        context.startActivity(intent);
        switchingScreen = true;
        HelperMethods.activityTransitionBack(context);
        HelperMethods.disableActivity(context);
    }

    private void calculateScores() {
        calculateEasyScore();
        calculateMediumScore();
        calculateHardScore();
        calculateVeryHardScore();
    }

    private void calculateEasyScore() {
        if (mPrefs2.getString("easyScore", "").length() == 0) {
            editor.putString("easyScore", "0");
            editor.apply();
        }
        if (!mPrefs.getString("easyScore", "").isEmpty() && !mPrefs2.getString("easyScore", "").isEmpty()) {
            if (Integer.parseInt(mPrefs.getString("easyScore", "")) > Integer.parseInt(mPrefs2.getString("easyScore", ""))) {
                editor.putString("easyScore", mPrefs.getString("easyScore", ""));
                editor.apply();
                easyScore = mPrefs.getString("easyScore", "");
                saveBestScores();
            } else {
                easyScore = mPrefs2.getString("easyScore", "");
                saveBestScores();
            }
        } else if (!mPrefs.getString("easyScore", "").isEmpty() && mPrefs2.getString("easyScore", "").isEmpty()) {
            easyScore = mPrefs.getString("easyScore", "");
            editor.putString("easyScore", mPrefs.getString("easyScore", ""));
            saveBestScores();
        }
    }

    private void calculateMediumScore() {
        if (mPrefs2.getString("mediumScore", "").length() == 0) {
            editor.putString("mediumScore", "0");
            editor.apply();
        }
        if (!mPrefs.getString("mediumScore", "").isEmpty() && !mPrefs2.getString("mediumScore", "").isEmpty()) {
            if (Integer.parseInt(mPrefs.getString("mediumScore", "")) > Integer.parseInt(mPrefs2.getString("mediumScore", ""))) {
                editor.putString("mediumScore", mPrefs.getString("mediumScore", ""));
                editor.apply();
                mediumScore = mPrefs.getString("mediumScore", "");
                saveBestScores();
            } else {
                mediumScore = mPrefs2.getString("mediumScore", "");
                saveBestScores();
            }
        } else if (!mPrefs.getString("mediumScore", "").isEmpty() && mPrefs2.getString("mediumScore", "").isEmpty()) {
            mediumScore = mPrefs.getString("mediumScore", "");
            editor.putString("mediumScore", mPrefs.getString("mediumScore", ""));
            saveBestScores();
        }
    }

    private void calculateHardScore() {
        if (mPrefs2.getString("hardScore", "").length() == 0) {
            editor.putString("hardScore", "0");
            editor.apply();
        }
        if (!mPrefs.getString("hardScore", "").isEmpty() && !mPrefs2.getString("hardScore", "").isEmpty()) {
            if (Integer.parseInt(mPrefs.getString("hardScore", "")) > Integer.parseInt(mPrefs2.getString("hardScore", ""))) {
                editor.putString("hardScore", mPrefs.getString("hardScore", ""));
                editor.apply();
                hardScore = mPrefs.getString("hardScore", "");
                saveBestScores();
            } else {
                hardScore = mPrefs2.getString("hardScore", "");
                saveBestScores();
            }
        } else if (!mPrefs.getString("hardScore", "").isEmpty() && mPrefs2.getString("hardScore", "").isEmpty()) {
            hardScore = mPrefs.getString("hardScore", "");
            editor.putString("hardScore", mPrefs.getString("hardScore", ""));
            saveBestScores();
        }
    }

    private void calculateVeryHardScore() {
        if (mPrefs2.getString("veryHardScore", "").length() == 0) {
            editor.putString("veryHardScore", "0");
            editor.apply();
        }
        if (!mPrefs.getString("veryHardScore", "").isEmpty() && !mPrefs2.getString("veryHardScore", "").isEmpty()) {
            if (Integer.parseInt(mPrefs.getString("veryHardScore", "")) > Integer.parseInt(mPrefs2.getString("veryHardScore", ""))) {
                editor.putString("veryHardScore", mPrefs.getString("veryHardScore", ""));
                editor.apply();
                veryHardScore = mPrefs.getString("veryHardScore", "");
                saveBestScores();
            } else {
                veryHardScore = mPrefs2.getString("veryHardScore", "");
                saveBestScores();
            }
        } else if (!mPrefs.getString("veryHardScore", "").isEmpty() && mPrefs2.getString("veryHardScore", "").isEmpty()) {
            veryHardScore = mPrefs.getString("veryHardScore", "");
            editor.putString("veryHardScore", mPrefs.getString("veryHardScore", ""));
            saveBestScores();
        }
    }

    private void saveBestScores() {
        tvEasyScore.setText(easyScore);
        tvMediumScore.setText(mediumScore);
        tvHardScore.setText(hardScore);
        tvVeryHardScore.setText(veryHardScore);
    }

    private void toggleResetButton() {
        if ((tvEasyScore.getText().toString().equals("0") && tvMediumScore.getText().toString().equals("0")
                && tvHardScore.getText().toString().equals("0") && tvVeryHardScore.getText().toString().equals("0"))) {
            btnReset.setEnabled(false);
        } else {
            btnReset.setEnabled(true);
        }
    }

    public boolean isSwitchingScreen() {
        return switchingScreen;
    }

}
