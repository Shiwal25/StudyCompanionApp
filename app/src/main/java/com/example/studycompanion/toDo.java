package com.example.studycompanion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class toDo extends AppCompatActivity {

    ImageView imageView;
    TextView editText;
    ImageButton imageButton;
    RecyclerView recyclerView;
    ArrayList<String> arrayList;
    Context context;
    todoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_to_do);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageView = findViewById(R.id.back_todo);
        imageButton = findViewById(R.id.ButtonAddTask);
        editText = findViewById(R.id.editTextAddTask);
        recyclerView = findViewById(R.id.recycle_todo);
        arrayList = new ArrayList<>();
        context = getApplicationContext();
        SharedPreferences sharedPreferences = getSharedPreferences("todoList",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new todoAdapter(this, arrayList, updatedList -> {
            // Update SharedPreferences when notified by the adapter
            Set<String> updatedSet = new HashSet<>(updatedList);
            editor.putStringSet("todo_list", updatedSet);
            editor.apply();
        });
        recyclerView.setAdapter(adapter);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

          Set<String> set = sharedPreferences.getStringSet("todo_list", new HashSet<>());
          arrayList = new ArrayList<>(set);
        adapter = new todoAdapter(this, arrayList, updatedList -> {
            // Update SharedPreferences when notified by the adapter
            Set<String> updatedSet = new HashSet<>(updatedList);
            editor.putStringSet("todo_list", updatedSet);
            editor.apply();
        });
//          todoAdapter adapter = new todoAdapter(this, arrayList, updatedList);
          recyclerView.setAdapter(adapter);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTask = editText.getText().toString().trim();
                if (!newTask.isEmpty()) {
                    arrayList.add(newTask);
                    adapter.notifyItemInserted(arrayList.size() - 1);
                    editText.setText("");
                    Set<String> updatedSet = new HashSet<>(arrayList);
                    editor.putStringSet("todo_list", updatedSet);
                    editor.apply();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                }
            }});
    }
}