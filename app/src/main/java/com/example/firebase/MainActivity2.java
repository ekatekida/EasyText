package com.example.firebase;

import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {
    TextView name_tv, comment_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        Button Back;
        String name = getIntent().getStringExtra("name");
        name_tv = findViewById(R.id.name_tv);
        name_tv.setText(name);
        String comment = getIntent().getStringExtra("comment");
        comment_tv = findViewById(R.id.comment_tv);
        comment_tv.setText(comment);
        String text = getIntent().getStringExtra("text");
        TextFr Fragment = TextFr.newInstance(text);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView2, Fragment)
                .commit();
        Back = findViewById(R.id.back_btn);
        Back.setOnClickListener(v -> {
            finish();
        });
    }
}