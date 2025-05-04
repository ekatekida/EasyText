package com.example.firebase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.firebase.databinding.ActivityMainBinding;
import com.example.firebase.databinding.ActivityRegistrationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registration extends AppCompatActivity {
    private FirebaseAuth auth;
    private ActivityRegistrationBinding binding;
    private SharedPreferences settings;
    private static final String PREFS_FILE = "Account";
    private static final String PREF_EMAIL = "Name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        auth = FirebaseAuth.getInstance();
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        settings = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);
        setContentView(view);
        binding.signUp.setOnClickListener(v -> {
            String email = binding.editEmail.getText().toString();
            String password = binding.editPassword.getText().toString();
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(
                    Registration.this, task -> {
                        if(task.isSuccessful()) {
                            SharedPreferences.Editor prefEditor = settings.edit();
                            prefEditor.putString(PREF_EMAIL, email);
                            prefEditor.apply();
                            binding.warning.setText("");
                            openMainActivity();
                            Log.d("RRR","Sign Up success: "+task.getResult().getUser().getUid());
                            Toast.makeText(Registration.this, "Успешная регистрация аккаунта: "+email, Toast.LENGTH_SHORT).show();
                        } else {
                            if (binding.editPassword.getText().toString().length() <6){
                                binding.warning.setText("Пароль должен быть минимум 6 символов");
                            } else {
                                Log.d("RRR", "Error: " + task.getException());
                                Toast.makeText(Registration.this, "Ошибка регистрации", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });
        binding.signIn.setOnClickListener(v -> {
            String email = binding.editEmail.getText().toString();
            String password = binding.editPassword.getText().toString();
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(
                    Registration.this, task -> {
                        if(task.isSuccessful()) {
                            SharedPreferences.Editor prefEditor = settings.edit();
                            prefEditor.putString(PREF_EMAIL, email);
                            prefEditor.apply();
                            binding.warning.setText("");
                            openMainActivity();
                            Log.d("RRR","Auth success: "+task.getResult().getUser().getUid());
                            Toast.makeText(Registration.this, "Успешный вход в аккаунт:"+email, Toast.LENGTH_SHORT).show();
                        } else {
                            if (binding.editPassword.getText().toString().length() <6){
                                binding.warning.setText("Пароль должен быть минимум 6 символов");
                            } else {
                                Log.d("RRR", "Error: " + task.getException());
                                Toast.makeText(Registration.this, "Ошибка входа", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });
    }
    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        this.startActivity(intent);
    }
}