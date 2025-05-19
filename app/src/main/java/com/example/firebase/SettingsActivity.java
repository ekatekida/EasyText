package com.example.firebase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private static final String PREFS_FILE = "Account";
    private static final String PREF_EMAIL = "Name";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        findViewById(R.id.small).setOnClickListener(v -> setFontSize(FontManager.SMALL));
        findViewById(R.id.medium).setOnClickListener(v -> setFontSize(FontManager.MEDIUM));
        findViewById(R.id.big).setOnClickListener(v -> setFontSize(FontManager.LARGE));
        SharedPreferences settings;
        settings = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);
        findViewById(R.id.sign_out_btn).setOnClickListener(v->{
            SharedPreferences.Editor prefEditor = settings.edit();
            prefEditor.putString(PREF_EMAIL, "-");
            prefEditor.apply();
            Intent intent = new Intent(this, Registration.class);
            finish();
            this.startActivity(intent);
        });
    }
    private void setFontSize(float size) {
        FontManager.saveFontSize(this, size);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}