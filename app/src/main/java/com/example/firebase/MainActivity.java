package com.example.firebase;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private FirebaseFirestore db;
    private CollectionReference collection;
    private FragmentManager fm;
    ArrayList<Note> arrayList = new ArrayList<>();
    MyAdapter adapter = new MyAdapter(arrayList, this);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        View promptsView = li.inflate(R.layout.window, null);
        AlertDialog.Builder mDialogBuilder= new AlertDialog.Builder(MainActivity.this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        db = FirebaseFirestore.getInstance();
        btn = findViewById(R.id.button);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btn.setOnClickListener((View v) -> {
            mDialogBuilder.setView(promptsView);
            mDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK", (dialog, id) -> {
                        EditText userName =  promptsView.findViewById(R.id.editName);
                        EditText userComment = promptsView.findViewById(R.id.editComment);
                        EditText userText = promptsView.findViewById(R.id.editText);
                        Log.d(TAG, userName.getText().toString());
                        Note note = new Note(userName.getText().toString(), userComment.getText().toString(), userText.getText().toString());
                        arrayList.add(note);
                        doSave(note);
                    })
                    .setNegativeButton("Отмена",
                            (dialog, id) -> dialog.cancel());
            AlertDialog alertDialog = mDialogBuilder.create();

            if (promptsView.getParent() != null) {
                ViewGroup parent = (ViewGroup) promptsView.getParent();
                parent.removeView(promptsView);
            }
            alertDialog.show();
        });

        db.collection("Notes")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            Note data = document.toObject(Note.class);
                            arrayList.add(data);
                            recyclerView.setAdapter(adapter);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.w("Firestore", "Error getting documents.", task.getException());
                    }
                });
    }
    public void saving(Note note){
        collection.document(note.getName()).set(note)
                .addOnSuccessListener(aVoid -> {
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Запись '" + note.getName() + "' сохрнена!", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> {
                    Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, e.toString());
                });

    }

    public void doSave(Note note) {
        collection = db.collection("Notes");
        db.collection("Notes")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        boolean f = true;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document.getId() == note.getName()) {
                                f = false;
                            }}
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


}

