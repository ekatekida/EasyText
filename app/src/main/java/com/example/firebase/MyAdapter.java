package com.example.firebase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<Note> dataList;
    private Context context;


    public MyAdapter(ArrayList<Note> notes, Context context) {
        this.dataList = notes;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note data = dataList.get(position);
        Log.d("RRR","onBindViewHolder");
        holder.textViewField1.setText(data.getName().toString());
        holder.textViewField2.setText(data.getComment().toString());

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
        TextView textViewField1;
        TextView textViewField2;
        Button btnMenu;
        public ViewHolder(View itemView) {
            super(itemView);
            btnMenu = itemView.findViewById(R.id.btn_menu);

            btnMenu.setOnClickListener(v -> {
                showContextMenu(itemView, getPosition());
            });
            textViewField1 = itemView.findViewById(R.id.textView);
            textViewField2 = itemView.findViewById(R.id.textView2);
        }
    }
    public void removeItem(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }
    private void showContextMenu(View view, int position) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        popup.getMenuInflater().inflate(R.menu.menu_item, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.delete){

                db.collection("Notes").document(dataList.get(position).getName())
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
