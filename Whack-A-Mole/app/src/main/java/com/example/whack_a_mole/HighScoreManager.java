package com.example.whack_a_mole;

import android.content.Context;
import android.content.SharedPreferences;

public class HighScoreManager {
    private static final String PREF_NAME = "HighScorePrefs";
    private static final String HIGH_SCORE_KEY = "highScore";

    public static int getHighScore(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(HIGH_SCORE_KEY, 0); // Default high score is 0
    }

    public static void setHighScore(Context context, int highScore) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(HIGH_SCORE_KEY, highScore);
        editor.apply();
    }
}
