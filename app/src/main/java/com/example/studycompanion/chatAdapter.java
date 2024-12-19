package com.example.studycompanion;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class chatAdapter extends RecyclerView.Adapter<chatAdapter.ViewHolder> {

    android.content.Context context;
    ArrayList<String> arrayListU;
    ArrayList<String> arrayListG;
    chatAdapter(Context context, ArrayList<String> arrayListU, ArrayList<String> arrayListG) {
        this.context = context;
        this.arrayListU = arrayListU;
        this.arrayListG = arrayListG;
    }

    @NonNull
    @Override
    public chatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chatrv_card, parent, false);
        chatAdapter.ViewHolder viewHolder = new chatAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull chatAdapter.ViewHolder holder, int position) {
        if (position < arrayListU.size() && position < arrayListG.size()){
            holder.editText1.setText(arrayListU.get(position));
            holder.editText2.setText(arrayListG.get(position));
        }
        else {
            Log.d("Shiwal2510", "onBindViewHolder: ChatAdapter Invalid position:  position for lists sizes: U=" +
                    arrayListU.size() + " G=" + arrayListG.size());
        }
    }

    @Override
    public int getItemCount() {
        return arrayListU.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView editText1,editText2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            editText1 = itemView.findViewById(R.id.UserPrompt);
            editText2 = itemView.findViewById(R.id.GeminiResponse);
        }
    }
}
