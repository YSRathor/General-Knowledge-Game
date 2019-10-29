package com.example.generalknowledgegame.data;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.example.generalknowledgegame.R;

public class MenuSoundService extends Service {
    private static final String TAG = null;
    private MediaPlayer player;
    private static Boolean firstStart = true;
    private static Boolean switchingScreens = false;

    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.menu_music);
        player.setLooping(true); // Set looping
        player.setVolume(100, 100);
    }

    @SuppressLint("WrongConstant")
    public int onStartCommand(Intent intent, int flags, int startId) {
        SharedPreferences mPrefs = getSharedPreferences("music", 0);

        if (!mPrefs.getString("time", "").isEmpty() && !firstStart && !switchingScreens) {
            player.seekTo(Integer.parseInt(mPrefs.getString("time", "")));
            player.start();
        } else if (!mPrefs.getString("time", "").isEmpty() && firstStart && !switchingScreens) {
            player.start();
            firstStart = false;
        } else {
            player.start();
        }

        return 1;
    }

    @Override
    public void onDestroy() {
        if (player != null) {
            int playTime = player.getCurrentPosition();

            SharedPreferences mPrefs = getSharedPreferences("music", 0);
            SharedPreferences.Editor editor = mPrefs.edit();

            editor.putString("time", String.valueOf(playTime));
            editor.apply();
        }
        assert player != null;
        player.stop();
        player.release();
    }

    public static void notFirstStart() {
        firstStart = false;
    }

    public static void setSwitchingScreens(boolean b) {
        switchingScreens = b;
    }

}