package com.example.studycompanion;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class todoAdapter extends RecyclerView.Adapter<todoAdapter.ViewHolder> {

    android.content.Context context;
    ArrayList<String> arrayList;
    OnTaskChangeListener listener;

    todoAdapter(Context context, ArrayList<String> arrayList, OnTaskChangeListener listener) {
        this.context = context;
        this.arrayList = arrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.todo_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.editText.setText(arrayList.get(position));

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, arrayList.size());
                listener.onTaskListChanged(arrayList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView editText;
        ImageButton imageButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            editText = itemView.findViewById(R.id.todoText);
            imageButton = itemView.findViewById(R.id.todoDelete);
        }
    }
    public interface OnTaskChangeListener {
        void onTaskListChanged(ArrayList<String> updatedList);
    }
}
