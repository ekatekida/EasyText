package com.example.firebase;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.firebase.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String PREFS_FILE = "Account";
    private static final String PREF_EMAIL = "Name";
    private FirebaseAuth auth;
    private SharedPreferences settings;
    private FirebaseFirestore db;
    private CollectionReference collection;
    private String uid;
    private ActivityMainBinding binding;
    public ArrayList<Note> arrayList = new ArrayList<>();
    public MyAdapter adapter = new MyAdapter(arrayList, this);
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(FontManager.applyFontSize(newBase));
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        settings = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);
        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        View promptsView = li.inflate(R.layout.window, null);
        AlertDialog.Builder mDialogBuilder= new AlertDialog.Builder(MainActivity.this);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        String name = settings.getString(PREF_EMAIL,"-");
        if (name.equals("-")){
            Intent intent = new Intent(this, Registration.class);
            this.startActivity(intent);
        }else{
            FirebaseUser user = auth.getCurrentUser();
            uid = user.getUid();
            SharedPreferences.Editor prefEditor = settings.edit();
            prefEditor.putString("UID", uid);
            prefEditor.apply();
            Log.d("User UID", uid);
        }

        binding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });

        binding.settingsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            MainActivity.this.startActivity(intent);
        });

        binding.signOutBtn.setOnClickListener(v->{
            SharedPreferences.Editor prefEditor = settings.edit();
            prefEditor.putString(PREF_EMAIL, "-");
            prefEditor.apply();
            Intent intent = new Intent(this, Registration.class);
            finish();
            this.startActivity(intent);
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.button.setOnClickListener((View v) -> {
            mDialogBuilder.setView(promptsView);
            mDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK", (dialog, id) -> {
                        EditText userName =  promptsView.findViewById(R.id.editName);
                        EditText userComment = promptsView.findViewById(R.id.editComment);
                        EditText userText = promptsView.findViewById(R.id.EditComment);
                        Log.d(TAG, userName.getText().toString());
                        Note note = new Note(userName.getText().toString(), userComment.getText().toString(), userText.getText().toString().replace("\n", "<br>"));
                        if (note.getName().isEmpty()){
                            note.setName("<Без заголовка>");
                        }
                        if (note.getComment().isEmpty()){
                            note.setComment("<Без комментария>");
                        }
                        if (note.getText().isEmpty()){
                            note.setTexts("<Без текста>");
                        }
                        arrayList.add(note);
                        doSave(note);
                    })
                    .setNegativeButton(R.string.cancel,
                            (dialog, id) -> dialog.cancel());
            AlertDialog alertDialog = mDialogBuilder.create();
            if (promptsView.getParent() != null) {
                ViewGroup parent = (ViewGroup) promptsView.getParent();
                parent.removeView(promptsView);
            }
            alertDialog.show();
        });

        db.collection(settings.getString("UID","-"))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            Note data = document.toObject(Note.class);
                            arrayList.add(data);
                        }
                        binding.recyclerView.setAdapter(adapter);
                        adapter.filter("");
                    } else {
                        Log.w("Firestore", "Error getting documents.", task.getException());
                    }
                });
    }
    public void saving(Note note){
        collection.document(note.getName()).set(note)
                .addOnSuccessListener(aVoid -> {
                    Log.d("RRRRRRRRRRRRRRR", arrayList.get(0).getName());
                    binding.recyclerView.setAdapter(adapter);
                    adapter.filter("");
                    Toast.makeText(MainActivity.this, "Запись '" + note.getName() + "' сохрнена!", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> {
                    Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, e.toString());
                });
    }

    public void doSave(Note note) {
        collection = db.collection(settings.getString("UID","-"));
        db.collection(settings.getString("UID","-"))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        boolean f = true;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document.getId() == note.getName()) {
                                f = false;
                            }
                        }
                            if (f){
                                saving(note);
                            }else {
                                Toast.makeText(MainActivity.this, "Запись с таким названием уже существует!", Toast.LENGTH_SHORT).show();
                            }
                    } else{
                        Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

}

