package com.example.firebase;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firebase.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity {
    private ActivityMain2Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        String name = getIntent().getStringExtra("name");
        binding.nameTv.setText(name);
        String comment = getIntent().getStringExtra("comment");
        binding.commentTv.setText(comment);
        String text = getIntent().getStringExtra("text");
        TextFr Fragment = TextFr.newInstance(text);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView2, Fragment)
                .commit();
        binding.backBtn.setOnClickListener(v -> {
            finish();
        });
    }
}