package com.example.generalknowledgegame.data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.generalknowledgegame.R;

import java.lang.reflect.Method;

public class HelperMethods {

    static String capitaliseFirstLetter(String s) {
        return s.toUpperCase().charAt(0) + s.substring(1);
    }

    static String capitaliseAllLetters(String s) {
        return s.toUpperCase();
    }

    public static SpannableString customFontSizes(String s, float size, int start, int end) {
        SpannableString ss1 = new SpannableString(s);
        ss1.setSpan(new RelativeSizeSpan(size), start, end, 0);
        return ss1;
    }

    public static void startService(Context c, Class cs) {
        Intent svc = new Intent(c.getApplicationContext(), cs);
        c.startService(svc);
    }

    public static void stopService(Context c, Class cs) {
        Intent svc = new Intent(c.getApplicationContext(), cs);
        c.stopService(svc);
    }

    public static void animateButton(Button btn) {
        Animation anim = new ScaleAnimation(0.9f, 0.9f, 0.9f, 0.9f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        btn.startAnimation(anim);
    }

    public static void animateImageButton(ImageButton btn) {
        Animation anim = new ScaleAnimation(0.9f, 0.9f, 0.9f, 0.9f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        btn.startAnimation(anim);
    }

    @SuppressWarnings({"JavaReflectionMemberAccess", "ConstantConditions"})
    public static int returnTheme(Context c) {
        try {
            Class<?> wrapper = Context.class;
            Method method = wrapper.getMethod("getThemeResId");
            method.setAccessible(true);
            return (Integer) method.invoke(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void reloadActivity(Context c) {
        ((AppCompatActivity) c).finish();
        ((AppCompatActivity) c).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        c.startActivity(((AppCompatActivity) c).getIntent());
        ((AppCompatActivity) c).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public static void activityTransitionForward(Context c) {
        ((AppCompatActivity) c).overridePendingTransition(R.anim.activity_entry_animation, R.anim.activity_exit_animation);
    }

    public static void activityTransitionBack(Context c) {
        ((AppCompatActivity) c).overridePendingTransition(R.anim.activity_entry_animation_2, R.anim.activity_exit_animation_2);
    }

    public static void loadingActivity(final Context c, int time) {
        disableActivity(c);
        Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {
                enableActivity(c);
            }
        };
        handler.postDelayed(r, time);
    }

    public static void disableActivity(Context c) {
        ((AppCompatActivity) c).getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public static void enableActivity(Context c) {
        ((AppCompatActivity) c).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public static String resetBestScoreTextColour(Context c) {
        String textColour = "";

        if (returnTheme(c) == R.style.AppTheme) {
            textColour = "#000000";
        } else if (returnTheme(c) == R.style.AppTheme2) {
            textColour = "#FFFFFF";
        }

        return textColour;
    }

    public static int setBestScoreTextColour(Context c) {
        int textColour = 0;

        if (returnTheme(c) == R.style.AppTheme) {
            textColour = R.color.customRed2;
        } else if (returnTheme(c) == R.style.AppTheme2) {
            textColour = R.color.customYellow4;
        }

        return textColour;
    }

    public static int setDifficultyTextColour(Context c, String difficulty) {
        int textColour = 0;

        if (returnTheme(c) == R.style.AppTheme) {
            switch (difficulty) {
                case "easy":
                    textColour = R.color.easyColour;
                    break;

                case "medium":
                    textColour = R.color.mediumColour;
                    break;

                case "hard":
                    textColour = R.color.hardColour;
                    break;

                case "very hard":
                    textColour = R.color.veryHardColour;
                    break;
            }
        }

        if (returnTheme(c) == R.style.AppTheme2) {
            switch (difficulty) {
                case "easy":
                    textColour = R.color.customGreen4;
                    break;

                case "medium":
                    textColour = R.color.customYellow4;
                    break;

                case "hard":
                    textColour = R.color.customOrange3;
                    break;

                case "very hard":
                    textColour = R.color.customRed4;
                    break;
            }
        }

        return textColour;

    }

    public static void setTheme(Context c) {
        SharedPreferences themeStorage = c.getSharedPreferences("Themes", 0);

        if (!themeStorage.getString("theme", "").isEmpty()) {
            c.setTheme(Integer.valueOf(themeStorage.getString("theme", "")));
        }
    }

    public static void setCorrectView(Context c, int small, int smallV2, int normal) {
        Configuration config = c.getResources().getConfiguration();

        if (config.screenHeightDp >= 569 && config.screenHeightDp <= 740) {
            if (config.smallestScreenWidthDp == 360) {
                ((AppCompatActivity) c).setContentView(small);
            } else if (config.smallestScreenWidthDp >= 411) {
                ((AppCompatActivity) c).setContentView(smallV2);
            }
        } else if (config.screenHeightDp >= 741 && config.screenHeightDp <= 960) {
            ((AppCompatActivity) c).setContentView(normal);
        }
    }

}
