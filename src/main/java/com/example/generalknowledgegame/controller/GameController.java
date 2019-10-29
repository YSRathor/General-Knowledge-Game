package com.example.generalknowledgegame.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.generalknowledgegame.R;
import com.example.generalknowledgegame.data.HelperMethods;
import com.example.generalknowledgegame.data.ImportQuestions;
import com.example.generalknowledgegame.model.Question;
import com.example.generalknowledgegame.model.QuestionBank;
import com.example.generalknowledgegame.view.GameOverScreen;
import com.example.generalknowledgegame.view.HomeScreen;

import java.util.Locale;

public class GameController {

    private Context context;
    private TextView tvGameTitle, tvQuestionText, tvDifficultySetting, tvScore, tvBestScore;
    private ImageButton btnHome;
    private ImageButton btnPause;
    private Button btnA, btnB, btnC, btnD;
    private ImportQuestions imQ;
    private int n;
    private QuestionBank model;

    private boolean scoreUnbeaten = true;

    private int oldBestEasyScore = 0;
    private int oldBestMediumScore = 0;
    private int oldBestHardScore = 0;
    private int oldBestVeryHardScore = 0;

    private boolean switchingScreen = false;

    private boolean appPaused = false;
    private boolean goingHome = false;

    public GameController(Context c, ImportQuestions iq, int i, QuestionBank qb, TextView tvGT, TextView tvQT,
                          ImageButton A, ImageButton B, Button C, Button D, Button E, Button F, TextView tvDS, TextView tvS, TextView tvBS) {
        this.context = c;
        this.imQ = iq;
        this.n = i;
        this.model = qb;
        this.tvGameTitle = tvGT;
        this.tvQuestionText = tvQT;
        this.btnHome = A;
        this.btnPause = B;
        this.btnA = C;
        this.btnB = D;
        this.btnC = E;
        this.btnD = F;
        this.tvDifficultySetting = tvDS;
        this.tvScore = tvS;
        this.tvBestScore = tvBS;
    }

    public void setupGame() {
        homeButton(btnHome);
        pauseGame(btnPause);
        setBestScore();
        changeQuestion();
        changeValue();
    }

    private void setBestScore() {
        SharedPreferences mPrefs = context.getSharedPreferences("IDValue", 0);
        SharedPreferences mPrefs2 = context.getSharedPreferences("IDValue2", 0);

        if (model.getNoOfQuestions() == 10) {
            tvDifficultySetting.setText(R.string.easy_text);
            tvDifficultySetting.setTextColor(ContextCompat.getColor(context, HelperMethods.setDifficultyTextColour(context, "easy")));
            if (!mPrefs2.getString("easyScore", "").isEmpty() && !mPrefs.getString("easyScore", "").isEmpty()) {
                boolean bestEasyScore = Integer.parseInt(mPrefs2.getString("easyScore", "")) > Integer.parseInt(mPrefs.getString("easyScore", ""));
                if (bestEasyScore) {
                    tvBestScore.setText(String.format(Locale.UK, "Best: %d", Integer.parseInt(mPrefs2.getString("easyScore", ""))));
                    oldBestEasyScore = Integer.parseInt(mPrefs2.getString("easyScore", ""));
                } else {
                    tvBestScore.setText(String.format(Locale.UK, "Best: %d", Integer.parseInt(mPrefs.getString("easyScore", ""))));
                    oldBestEasyScore = Integer.parseInt(mPrefs.getString("easyScore", ""));
                    mPrefs2.edit().putString("easyScore", mPrefs.getString("easyScore", "")).apply();
                }
            }
        } else if (model.getNoOfQuestions() == 20) {
            tvDifficultySetting.setText(R.string.medium_text);
            tvDifficultySetting.setTextColor(ContextCompat.getColor(context, HelperMethods.setDifficultyTextColour(context, "medium")));
            if (!mPrefs2.getString("mediumScore", "").isEmpty() && !mPrefs.getString("mediumScore", "").isEmpty()) {
                boolean bestMediumScore = Integer.parseInt(mPrefs2.getString("mediumScore", "")) > Integer.parseInt(mPrefs.getString("mediumScore", ""));
                if (bestMediumScore) {
                    tvBestScore.setText(String.format(Locale.UK, "Best: %d", Integer.parseInt(mPrefs2.getString("mediumScore", ""))));
                    oldBestMediumScore = Integer.parseInt(mPrefs2.getString("mediumScore", ""));
                } else {
                    tvBestScore.setText(String.format(Locale.UK, "Best: %d", Integer.parseInt(mPrefs.getString("mediumScore", ""))));
                    oldBestMediumScore = Integer.parseInt(mPrefs.getString("mediumScore", ""));
                    mPrefs2.edit().putString("mediumScore", mPrefs.getString("mediumScore", "")).apply();
                }
            }
        } else if (model.getNoOfQuestions() == 50) {
            tvDifficultySetting.setText(R.string.hard_text);
            tvDifficultySetting.setTextColor(ContextCompat.getColor(context, HelperMethods.setDifficultyTextColour(context, "hard")));
            if (!mPrefs2.getString("hardScore", "").isEmpty() && !mPrefs.getString("hardScore", "").isEmpty()) {
                boolean bestHardScore = Integer.parseInt(mPrefs2.getString("hardScore", "")) > Integer.parseInt(mPrefs.getString("hardScore", ""));
                if (bestHardScore) {
                    tvBestScore.setText(String.format(Locale.UK, "Best: %d", Integer.parseInt(mPrefs2.getString("hardScore", ""))));
                    oldBestHardScore = Integer.parseInt(mPrefs2.getString("hardScore", ""));
                } else {
                    tvBestScore.setText(String.format(Locale.UK, "Best: %d", Integer.parseInt(mPrefs.getString("hardScore", ""))));
                    oldBestHardScore = Integer.parseInt(mPrefs.getString("hardScore", ""));
                    mPrefs2.edit().putString("hardScore", mPrefs.getString("hardScore", "")).apply();
                }
            }

        } else if (model.getNoOfQuestions() == 100) {
            tvDifficultySetting.setText(R.string.very_hard_text);
            tvDifficultySetting.setTextColor(ContextCompat.getColor(context, HelperMethods.setDifficultyTextColour(context, "very hard")));
            if (!mPrefs2.getString("veryHardScore", "").isEmpty() && !mPrefs.getString("veryHardScore", "").isEmpty()) {
                boolean bestVeryHardScore = Integer.parseInt(mPrefs2.getString("veryHardScore", "")) > Integer.parseInt(mPrefs.getString("veryHardScore", ""));
                if (bestVeryHardScore) {
                    tvBestScore.setText(String.format(Locale.UK, "Best: %d", Integer.parseInt(mPrefs2.getString("veryHardScore", ""))));
                    oldBestVeryHardScore = Integer.parseInt(mPrefs2.getString("veryHardScore", ""));
                } else {
                    tvBestScore.setText(String.format(Locale.UK, "Best: %d", Integer.parseInt(mPrefs.getString("veryHardScore", ""))));
                    oldBestVeryHardScore = Integer.parseInt(mPrefs.getString("veryHardScore", ""));
                    mPrefs2.edit().putString("veryHardScore", mPrefs.getString("veryHardScore", "")).apply();
                }
            }
        }

    }

    private void changeQuestion() {
        if (model.getQuestion(model.getQuestionNo()) != null) {
            tvGameTitle.setText(String.format(Locale.UK, "Question %d", (model.getQuestionNo() + 1)));
            tvScore.setText(String.format(Locale.UK, "Score: %d", model.getScoreCount()));

            tvQuestionText.startAnimation(objectAnimation(R.anim.slide_down_animation));
            tvQuestionText.setText(model.getQuestion(model.getQuestionNo()).getQuestion());

            animateButtons();

            btnHome.setEnabled(true);
            btnPause.setEnabled(true);

            btnA.setText(model.getQuestion(model.getQuestionNo()).getOptionA());
            btnB.setText(model.getQuestion(model.getQuestionNo()).getOptionB());
            btnC.setText(model.getQuestion(model.getQuestionNo()).getOptionC());
            btnD.setText(model.getQuestion(model.getQuestionNo()).getOptionD());
        }
        changeValue();
    }

    private void buttonLogic(final Button btn, final Button btn2, final Button btn3, final Button btn4) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableButtons(btn, btn2, btn3, btn4);
                disableButtonClicks(btn, btn2, btn3, btn4);
                HelperMethods.animateButton(btn);
                btnHome.setEnabled(false);
                btnPause.setEnabled(false);
                HelperMethods.loadingActivity(context, 1250);
                validateAnswer(btn, model.getQuestion(model.getQuestionNo()));
                enableButtons(btn, btn2, btn3, btn4);
            }
        });
    }

    private void changeValue() {
        buttonLogic(btnA, btnB, btnC, btnD);
        buttonLogic(btnB, btnA, btnC, btnD);
        buttonLogic(btnC, btnB, btnA, btnD);
        buttonLogic(btnD, btnA, btnB, btnC);
    }

    private void validateAnswer(final Button btn, Question q) {
        final MediaPlayer correctSound = MediaPlayer.create(context.getApplicationContext(), R.raw.correct_answer_sound);
        final MediaPlayer wrongSound = MediaPlayer.create(context.getApplicationContext(), R.raw.wrong_answer_sound);

        if (model.getQuestionNo() != model.getNoOfQuestions()) {
            if (q.getAnswer().contains(btn.getText())) {
                setButtonColour(btn, R.drawable.correct_button, "#000000");
                correctSound.start();

                model.setScoreCount(model.getScoreCount() + 1);
                String s = String.valueOf(model.getScoreCount());
                tvScore.setText(String.format(Locale.UK, "Score: %s", s));
                checkBestScore();

                questionTransition(btn, R.drawable.green_tick, "Correct Answer!");
            } else {
                setButtonColour(btn, R.drawable.wrong_button, "#000000");
                wrongSound.start();

                questionTransition(btn, R.drawable.red_cross, "Wrong Answer!");
                checkBestScore();
            }
        }
    }

    private void checkBestScore() {
        int bestScore = Integer.parseInt(tvBestScore.getText().toString().substring(6));

        if (model.getScoreCount() > bestScore && !scoreUnbeaten) {
            bestScore = model.getScoreCount();
            tvBestScore.setText(String.format(Locale.UK, "Best: %d", bestScore));
        } else if (model.getScoreCount() > bestScore && scoreUnbeaten) {
            bestScore = model.getScoreCount();
            tvBestScore.setText(String.format(Locale.UK, "Best: %d", bestScore));
            scoreUnbeaten = false;
            tvBestScore.setTextColor(ContextCompat.getColor(context, HelperMethods.setBestScoreTextColour(context)));
            final MediaPlayer cheers = MediaPlayer.create(context.getApplicationContext(), R.raw.cheering_sound);
            cheers.start();
        }
    }

    private void openGameOverScreen() {
        Intent intent = new Intent(context.getApplicationContext(), GameOverScreen.class);
        intent.putExtra("score", String.valueOf(model.getScoreCount()));
        intent.putExtra("questions", String.valueOf(model.getNoOfQuestions()));
        intent.putExtra("highScore", String.valueOf(scoreUnbeaten));
        context.startActivity(intent);
        switchingScreen = true;
        HelperMethods.activityTransitionForward(context);
        HelperMethods.disableActivity(context);
    }

    private void checkQuestionNo() {
        if (model.getQuestionNo() == model.getNoOfQuestions()) {
            if (!appPaused) {
                openGameOverScreen();
            }
        } else {
            changeQuestion();
        }
    }

    public void setAppPaused(boolean b) {
        appPaused = b;
    }

    private void homeButton(final ImageButton btn) {
        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn.setClickable(false);
                        HelperMethods.animateImageButton(btn);
                        homeDialog();
                    }
                }
        );
    }

    public void onBackPressed() {
        pause();
    }

    public void onResume() {
        if (model.getQuestionNo() == model.getNoOfQuestions()) {
            openGameOverScreen();
        }
    }

    public void onPause() {
        if (model.getQuestionNo() != model.getNoOfQuestions() && !goingHome) {
            pause();
        }
    }

    private void pauseGame(final ImageButton btn) {
        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn.setClickable(false);
                        HelperMethods.animateImageButton(btn);
                        pause();
                    }
                }
        );
    }

    private void pause() {
        AlertDialog.Builder a_builder = new AlertDialog.Builder(context);
        a_builder.setMessage("Game Paused")
                .setCancelable(false)
                .setPositiveButton("Resume", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        btnPause.setClickable(true);
                        HelperMethods.enableActivity(context);
                    }
                })
                .setNegativeButton("Restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        restartDialog();
                    }
                })
                .setNeutralButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        exitDialog();
                    }
                })
        ;
        AlertDialog alert = a_builder.create();
        alert.setTitle("General Knowledge Game");
        alert.show();
    }

    private void restart() {
        imQ.loadData(model, context);
        model.setScoreCount(0);
        model.setQuestionNo(0);
        tvScore.setText(String.format(Locale.UK, "Score %d", model.getScoreCount()));
        model.filterQuestions(n);
        resetBestScore();
        scoreUnbeaten = true;
        changeQuestion();
        changeValue();
        btnPause.setClickable(true);
        HelperMethods.loadingActivity(context, 500);
    }

    private void restartDialog() {
        AlertDialog.Builder a_builder = new AlertDialog.Builder(context);
        a_builder.setMessage("Are you sure you want to restart the game?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        restart();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        btnPause.setClickable(true);
                    }
                })
        ;
        AlertDialog alert = a_builder.create();
        alert.setTitle("General Knowledge Game");
        alert.show();
    }

    private void exitDialog() {
        AlertDialog.Builder a_builder = new AlertDialog.Builder(context);
        a_builder.setMessage("Are you sure you want to exit the game?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((AppCompatActivity) context).finishAffinity();
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        btnPause.setClickable(true);
                    }
                })
        ;
        AlertDialog alert = a_builder.create();
        alert.setTitle("General Knowledge Game");
        alert.show();
    }

    private void homeDialog() {
        AlertDialog.Builder a_builder = new AlertDialog.Builder(context);
        a_builder.setMessage("Are you sure you want go back to the home screen?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context.getApplicationContext(), HomeScreen.class);
                        context.startActivity(intent);
                        switchingScreen = true;
                        HelperMethods.activityTransitionBack(context);
                        HelperMethods.disableActivity(context);
                        goingHome = true;
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        btnHome.setClickable(true);
                    }
                })
        ;
        AlertDialog alert = a_builder.create();
        alert.setTitle("General Knowledge Game");
        alert.show();
    }

    private void delay(final Button btn) {
        Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {
                setAnswerButtonTheme(btn);
                model.setQuestionNo(model.getQuestionNo() + 1);
                checkQuestionNo();
            }
        };
        handler.postDelayed(r, 1000);
    }

    private void setAnswerButtonTheme(Button btn) {
        if (HelperMethods.returnTheme(context) == R.style.AppTheme) {
            setButtonColour(btn, R.drawable.answer_button, "#FFFFFF");
        } else if (HelperMethods.returnTheme(context) == R.style.AppTheme2) {
            setButtonColour(btn, R.drawable.answer_button2, "#000000");
        }
    }

    private Toast outputToast(int image, String message) {
        Toast toast = Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastContentView = (LinearLayout) toast.getView();
        ImageView imageView = new ImageView(context.getApplicationContext());
        imageView.setImageResource(image);
        toastContentView.addView(imageView, 0);
        toast.show();
        return toast;
    }

    private void delayToast(final int image, final String message) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                outputToast(image, message).cancel();
            }
        }, 750);
    }

    private void questionTransition(Button btn, int image, String message) {
        outputToast(image, message);
        delayToast(image, message);
        delay(btn);
    }

    private Animation objectAnimation(int animation) {
        return AnimationUtils.loadAnimation(context.getApplicationContext(),
                animation);
    }

    private void animateButtons() {
        btnA.startAnimation(objectAnimation(R.anim.slide_sideways_animation));
        btnB.startAnimation(objectAnimation(R.anim.slide_sideways_animation));
        btnC.startAnimation(objectAnimation(R.anim.slide_sideways_animation));
        btnD.startAnimation(objectAnimation(R.anim.slide_sideways_animation));
    }

    private void setButtonColour(Button btn, int id, String colour) {
        btn.setBackground((ContextCompat.getDrawable(context, id)));
        btn.setTextColor(Color.parseColor(colour));
    }

    private void resetBestScore() {
        if (model.getNoOfQuestions() == 10) {
            tvBestScore.setText(String.format(Locale.UK, "Best: %d", oldBestEasyScore));
        } else if (model.getNoOfQuestions() == 20) {
            tvBestScore.setText(String.format(Locale.UK, "Best: %d", oldBestMediumScore));
        } else if (model.getNoOfQuestions() == 50) {
            tvBestScore.setText(String.format(Locale.UK, "Best: %d", oldBestHardScore));
        } else if (model.getNoOfQuestions() == 100) {
            tvBestScore.setText(String.format(Locale.UK, "Best: %d", oldBestVeryHardScore));
        }
        tvBestScore.setTextColor(Color.parseColor(HelperMethods.resetBestScoreTextColour(context)));
    }

    private void disableButtonClicks(Button btn, Button btn2, Button btn3, Button btn4) {
        btn.setClickable(false);
        btn2.setClickable(false);
        btn3.setClickable(false);
        btn4.setClickable(false);
    }

    private void disableButtons(Button btn, Button btn2, Button btn3, Button btn4) {
        btn.setEnabled(false);
        btn2.setEnabled(false);
        btn3.setEnabled(false);
        btn4.setEnabled(false);
    }

    private void enableButtons(Button btn, Button btn2, Button btn3, Button btn4) {
        btn.setEnabled(true);
        btn2.setEnabled(true);
        btn3.setEnabled(true);
        btn4.setEnabled(true);
    }

    public boolean isSwitchingScreen() {
        return switchingScreen;
    }

}
