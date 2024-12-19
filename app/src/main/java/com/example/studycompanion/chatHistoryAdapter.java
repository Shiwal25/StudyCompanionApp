package com.example.studycompanion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class chatHistoryAdapter extends RecyclerView.Adapter <chatHistoryAdapter.ViewHolder> {
    Context context;
    ArrayList<String> arrayList;

    chatHistoryAdapter(Context context, ArrayList<String> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public chatHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chathistory_rv, parent, false);
        chatHistoryAdapter.ViewHolder viewHolder = new chatHistoryAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull chatHistoryAdapter.ViewHolder holder, int position) {
        holder.textView.setText(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.chatrvTextView);
        }
    }
}
