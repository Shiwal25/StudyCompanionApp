package com.example.studycompanion;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class chat extends AppCompatActivity {
    ImageButton imageButton;
    RecyclerView recyclerView;
    Context context;
    chatHistoryAdapter adapter;
    ArrayList<String> arrayList;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.chatHistoryRV);
        imageButton = findViewById(R.id.back_todo);
        context = getApplicationContext();
        mAuth = FirebaseAuth.getInstance();
        userId = FirebaseAuth.getInstance().getUid();
        db = FirebaseFirestore.getInstance();
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
            getItems(userId,db);
//            Log.d("RK1208", "onCreate: HEREEEEEEE"+arrayList);
//            recyclerView.setLayoutManager(new LinearLayoutManager(this));
//            adapter = new chatHistoryAdapter(this,arrayList);
//            recyclerView.setAdapter(adapter);

    }
    public void getItems(String userId,FirebaseFirestore db){
        ArrayList<String> arrayListtp = new ArrayList<>();
        db.collection("chatHistory").whereEqualTo("ID",userId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> chat = (Map<String, Object>) document.get("chat");
                        if (chat != null) {
                            List<String> userPrompt = (List<String>) chat.get("userPrompt");
                            if (userPrompt != null && !userPrompt.isEmpty()) {
                                arrayListtp.add(userPrompt.get(0));
                                Log.e("RK1208", "onComplete: "+arrayListtp );
                            }
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            arrayList = arrayListtp; // Assign the fetched data to the arrayList
                            Log.d("RK1208", "onCreate: HEREEEEEEE" + arrayList);

                            recyclerView.setLayoutManager(new LinearLayoutManager(chat.this));
                            adapter = new chatHistoryAdapter(chat.this, arrayList);
                            recyclerView.setAdapter(adapter);
                        }
                    });
                }
                else {
                    Toast.makeText(chat.this, "Error fetching chats", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
