package com.example.generalknowledgegame.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressBarAnimation extends Animation {

    private Context context;
    private TextView textView;
    private TextView textView2;
    private ProgressBar progressBar;
    private float from;
    private float to;
    private Button button;

    public ProgressBarAnimation(Context context, TextView textView, TextView textView2, ProgressBar progressBar, float from, float to, Button button) {
        this.context = context;
        this.textView = textView;
        this.textView2 = textView2;
        this.progressBar = progressBar;
        this.from = from;
        this.to = to;
        this.button = button;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = from + (to - from) * interpolatedTime;
        progressBar.setProgress((int) value);
        textView2.setText((int) value + "%");

        if (value == to) {
            Handler handler = new Handler();
            Runnable r = new Runnable() {
                public void run() {
                    runLoadingExit();
                }
            };
            handler.postDelayed(r, 1000);

            Handler handler2 = new Handler();
            Runnable r2 = new Runnable() {
                public void run() {
                    runContinueButton();
                }
            };
            handler2.postDelayed(r2, 2000);

            Handler handler3 = new Handler();
            Runnable r3 = new Runnable() {
                public void run() {
                    runEnabler();
                }
            };
            handler3.postDelayed(r3, 2200);
        }
    }

    private void runLoadingExit() {
        textView.setVisibility(View.GONE);
        textView2.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        textView.setAnimation(AnimationUtils.loadAnimation(context.getApplicationContext(), android.R.anim.fade_out));
        textView2.setAnimation(AnimationUtils.loadAnimation(context.getApplicationContext(), android.R.anim.fade_out));
        progressBar.setAnimation(AnimationUtils.loadAnimation(context.getApplicationContext(), android.R.anim.fade_out));
        HelperMethods.disableActivity(context);
    }

    private void runContinueButton() {
        button.setVisibility(View.VISIBLE);
        button.setAnimation(AnimationUtils.loadAnimation(context.getApplicationContext(), android.R.anim.fade_in));
    }

    private void runEnabler() {
        HelperMethods.enableActivity(context);
    }

}
