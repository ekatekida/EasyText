package com.example.firebase;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase.databinding.ViewitemBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<Note> dataList;
    private Context context;
    private static final String PREFS_FILE = "Account";

    public MyAdapter(ArrayList<Note> notes, Context context) {
        this.dataList = notes;
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
        Note data = dataList.get(position);
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
        return dataList.size();
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

    public void removeItem(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }

    private void showContextMenu(View view, int position) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Context context = view.getContext();
        SharedPreferences settings = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        popup.getMenuInflater().inflate(R.menu.menu_item, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.delete){
                Toast.makeText(context, settings.getString("UID","-"), Toast.LENGTH_SHORT).show();
                db.collection(settings.getString("UID","-")).document(dataList.get(position).getName())
                        .delete()
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(context, "Запись '"+dataList.get(position).getName()+"' удалена!", Toast.LENGTH_SHORT).show();
                            removeItem(position);
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(context, "Ошибка удаления", Toast.LENGTH_SHORT).show();
                        });
            }
            return false;
        });
        popup.show();
    }

}
