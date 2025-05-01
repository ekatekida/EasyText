package com.example.firebase;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private final float SMALL_SIZE = 12f;
    private final float MEDIUM_SIZE = 25f;
    private final float BIG_SIZE = 40f;
    private final float DEFAULT_SIZE = 14f;
    private TextView previewText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        previewText = findViewById(R.id.preview_text);
        float savedSize = sharedPreferences.getFloat("text_size", DEFAULT_SIZE);
        previewText.setTextSize(savedSize);

        findViewById(R.id.small).setOnClickListener(v -> setTextSize(SMALL_SIZE));
        findViewById(R.id.medium).setOnClickListener(v -> setTextSize(MEDIUM_SIZE));
        findViewById(R.id.big).setOnClickListener(v -> setTextSize(BIG_SIZE));
    }

    private void setTextSize(float size) {
        previewText.setTextSize(size);
        sharedPreferences.edit().putFloat("text_size", size).apply();
    }
}