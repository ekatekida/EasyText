package com.example.firebase;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase.databinding.ViewitemBinding;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<Note> dataList;
    private ArrayList<Note> filteredNotes;
    private Context context;
    private static final String PREFS_FILE = "Account";
    public SharedPreferences settings;
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public CollectionReference collection;
    public MyAdapter(ArrayList<Note> notes, Context context) {
        this.dataList = notes;
        this.filteredNotes= new ArrayList<>(notes);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewitemBinding binding = ViewitemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note data = filteredNotes.get(position);
        Log.d("RRR","onBindViewHolder");
        holder.binding.textView.setText(data.getName());
        holder.binding.textView2.setText(data.getComment());
        holder.itemView.setOnClickListener (v -> {
            Log.d("RRR","position:"+position);
            Intent intent = new Intent(context, MainActivity2.class);
            intent.putExtra("name", data.getName());
            intent.putExtra("comment", data.getComment());
            intent.putExtra("text", data.getText());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return filteredNotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ViewitemBinding binding;
        public ViewHolder(ViewitemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.btnMenu.setOnClickListener(v -> {
                showContextMenu(itemView, getPosition());
            });
        }
    }
    public void filter(String query) {
        filteredNotes.clear();
        if (query.isEmpty() ||  query.equals("")) {
            filteredNotes.addAll(dataList);
        } else {
            Toast.makeText(context, "fd "+query, Toast.LENGTH_SHORT).show();
            String filterPattern = query.toLowerCase().trim();
            for (Note note : dataList) {
                if (note.getName().toLowerCase().contains(filterPattern)) {
                    filteredNotes.add(note);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void deleting(int position){
        db.collection(settings.getString("UID","-")).document(dataList.get(position).getName())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    filteredNotes.remove(position);
                    dataList.remove(position);
                    notifyItemRemoved(position);
                });

    }


    private void showContextMenu(View view, int position) {
        Context context = view.getContext();
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        popup.getMenuInflater().inflate(R.menu.menu_item, popup.getMenu());
        settings = context.getSharedPreferences(PREFS_FILE, MODE_PRIVATE);
        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.delete){
                deleting(position);
                Toast.makeText(context, "Запись '"+dataList.get(position).getName()+"' удалена!", Toast.LENGTH_SHORT).show();
            } else if (item.getItemId() == R.id.edit){
                showEditDialog(dataList.get(position), position);
            }
            return false;
        });
        popup.show();
    }
    private void showEditDialog(Note note, int position) {
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.window, null);
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);
        mDialogBuilder.setView(promptsView);
        TextView tv = promptsView.findViewById(R.id.heading);
        tv.setText("Изменить запись");
        EditText noteName = promptsView.findViewById(R.id.editName);
        EditText noteComment = promptsView.findViewById(R.id.editComment);
        EditText noteText = promptsView.findViewById(R.id.EditComment);
        noteName.setText(note.getName());
        noteComment.setText(note.getComment());
        noteText.setText(note.getText().replace("<br>", "\n"));
        mDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, id) -> {
                    Note edited_note = new Note(noteName.getText().toString(), noteComment.getText().toString(), noteText.getText().toString().replace("\n", "<br>"));
                    if (edited_note.getName().isEmpty()) {
                        edited_note.setName("<Без заголовка>");
                    }
                    if (edited_note.getComment().isEmpty()) {
                        edited_note.setComment("<Без комментария>");
                    }
                    if (edited_note.getText().isEmpty()) {
                        edited_note.setTexts("<Без текста>");
                    }
                    filteredNotes.add(position+1, edited_note);
                    dataList.add(position+1, edited_note);
                    doSave(edited_note, position);
                })
                .setNegativeButton(R.string.cancel,
                        (dialog, id) -> dialog.cancel());
        AlertDialog alertDialog = mDialogBuilder.create();
        if (promptsView.getParent() != null) {
            ViewGroup parent = (ViewGroup) promptsView.getParent();
            parent.removeView(promptsView);
        }
        alertDialog.show();
    }
    public void doSave(Note note, int posToDel) {
        settings = context.getSharedPreferences(PREFS_FILE, MODE_PRIVATE);
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
                            saving(note, posToDel);
                        }else {
                            Toast.makeText(context, R.string.note_exist, Toast.LENGTH_SHORT).show();
                        }
                    } else{
                        Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void saving(Note note, int posToDel){
        collection.document(note.getName()).set(note)
                .addOnSuccessListener(aVoid -> {
                    deleting(posToDel);
                    notifyDataSetChanged();
                    Toast.makeText(context, context.getString(R.string.note_) + note.getName() + context.getString(R.string._saved), Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> {
                    Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, e.toString());
                });
    }
}
