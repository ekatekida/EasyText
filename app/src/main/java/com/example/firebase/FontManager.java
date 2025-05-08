package com.example.firebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

public class FontManager {
    private static final String PREFS_NAME = "Font_size";
    private static final String FONT_SIZE_KEY = "font_size";

    public static final float SMALL = 0.65f;
    public static final float MEDIUM = 1.0f;
    public static final float LARGE = 1.45f;

    public static void saveFontSize(Context context, float size) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putFloat(FONT_SIZE_KEY, size).apply();
    }

    public static float getFontSize(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getFloat(FONT_SIZE_KEY, MEDIUM);
    }

    public static Context applyFontSize(Context context) {
        float fontSize = getFontSize(context);
        Configuration config = context.getResources().getConfiguration();
        config.fontScale = fontSize;
        return context.createConfigurationContext(config);
    }
}