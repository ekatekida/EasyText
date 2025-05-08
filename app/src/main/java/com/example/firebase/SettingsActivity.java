package com.example.firebase;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        findViewById(R.id.small).setOnClickListener(v -> {setFontSize(FontManager.SMALL);});
        findViewById(R.id.medium).setOnClickListener(v -> setFontSize(FontManager.MEDIUM));
        findViewById(R.id.big).setOnClickListener(v -> setFontSize(FontManager.LARGE));


    }
    private void setFontSize(float size) {
        FontManager.saveFontSize(this, size);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}