package com.example.generalknowledgegame.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;

import com.example.generalknowledgegame.data.HelperMethods;
import com.example.generalknowledgegame.view.HomeScreen;

public class AboutController {

    private Context context;
    private ImageButton btnHome;

    private boolean switchingScreen = false;

    public AboutController(Context c, ImageButton A) {
        this.context = c;
        this.btnHome = A;
    }

    public void initialiseAbout() {
        homeButton(btnHome);
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

    private void goHome() {
        Intent intent = new Intent(context.getApplicationContext(), HomeScreen.class);
        context.startActivity(intent);
        switchingScreen = true;
        HelperMethods.activityTransitionBack(context);
        HelperMethods.disableActivity(context);
    }

    public boolean isSwitchingScreen() {
        return switchingScreen;
    }

}
