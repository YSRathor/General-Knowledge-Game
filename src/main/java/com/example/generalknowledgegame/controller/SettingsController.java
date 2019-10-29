package com.example.generalknowledgegame.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.generalknowledgegame.R;
import com.example.generalknowledgegame.data.HelperMethods;
import com.example.generalknowledgegame.view.HomeScreen;

public class SettingsController {

    private Context context;
    private ImageButton btnHome;
    private Button theme1Selector;
    private Button theme2Selector;
    private ImageView theme1Selected;
    private ImageView theme2Selected;

    private boolean switchingScreen = false;

    public SettingsController(Context c, ImageButton btnA, Button btnB, Button btnC, ImageView img1, ImageView img2) {
        this.context = c;
        this.btnHome = btnA;
        this.theme1Selector = btnB;
        this.theme2Selector = btnC;
        this.theme1Selected = img1;
        this.theme2Selected = img2;
    }

    public void initialiseSettings() {
        disableSelectedThemeButton();
        homeButton(btnHome);
        changeToTheme1(theme1Selector);
        changeToTheme2(theme2Selector);
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

    private void changeToTheme1(final Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.setClickable(false);
                HelperMethods.animateButton(btn);
                changeTheme(R.style.AppTheme);
                delayedReload("Default Theme has been applied!");
                btnHome.setEnabled(false);
            }
        });
    }

    private void changeToTheme2(final Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.setClickable(false);
                HelperMethods.animateButton(btn);
                changeTheme(R.style.AppTheme2);
                delayedReload("Dark Theme has been applied!");
                btnHome.setEnabled(false);
            }
        });
    }

    private void changeTheme(int theme) {
        SharedPreferences mPrefs = context.getSharedPreferences("Themes", 0);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString("theme", String.valueOf(theme));
        editor.apply();
    }

    private void disableSelectedThemeButton() {
        SharedPreferences mPrefs = context.getSharedPreferences("Themes", 0);

        theme1Selector.setClickable(false);
        theme2Selector.setClickable(false);

        if (mPrefs.getString("theme", "").equals(String.valueOf(R.style.AppTheme))) {
            disableTheme(theme1Selector, theme2Selected, theme2Selector, theme1Selected, R.string.theme1ButtonText2, R.string.theme2ButtonText);
        } else if (mPrefs.getString("theme", "").equals(String.valueOf(R.style.AppTheme2))) {
            disableTheme(theme2Selector, theme1Selected, theme1Selector, theme2Selected, R.string.theme2ButtonText2, R.string.theme1ButtonText);
        }
    }

    private void disableTheme(Button btn, ImageView i, Button btn2, ImageView i2, int textID, int text2ID) {
        btn.setEnabled(false);
        btn.setText(textID);
        i.setVisibility(View.GONE);
        btn2.setEnabled(true);
        btn2.setText(text2ID);
        i2.setVisibility(View.VISIBLE);
    }

    private void delayedReload(final String message) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                HelperMethods.reloadActivity(context);
                Toast toast = Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT);
                toast.show();
            }
        }, 250);
    }

    public void onBackPressed() {
        goHome();
    }

    private void goHome() {
        Intent intent = new Intent(context.getApplicationContext(), HomeScreen.class);
        context.startActivity(intent);
        switchingScreen = true;
        ((AppCompatActivity) context).overridePendingTransition(R.anim.activity_entry_animation_2, R.anim.activity_exit_animation_2);
        HelperMethods.disableActivity(context);
    }

    public boolean isSwitchingScreen() {
        return switchingScreen;
    }

}
