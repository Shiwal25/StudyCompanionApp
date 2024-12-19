package com.example.studycompanion;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;


public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView imageView;
    FirebaseAuth mAuth;
    String userId;
    Intent intent;
    NavigationView navigationView;
    FirebaseFirestore db;
    TextView textViewName;
    TextView textViewEmail;
    String nametp;
    String mailtp;
    LinearLayout linearLayout2;
    String apiKey;
    EditText editText;
    RecyclerView recyclerView;
    ArrayList<String> arrayListU;
    ArrayList<String> arrayListG;
    Context context;
    chatAdapter adapter;
    ImageView PromptSend;
    ProgressBar progressBar;
    String currentDocumentId;
    TextView temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
      //  setContentView(R.layout.header);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        progressBar = findViewById(R.id.progressBar);
        PromptSend = findViewById(R.id.PromptSend);
        recyclerView = findViewById(R.id.chatrv);
        arrayListU = new ArrayList<>();
        arrayListG = new ArrayList<>();
        context = getApplicationContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open("config.properties");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        apiKey = properties.getProperty("API_KEY");
        Log.d("agrawal", "onCreate: "+ apiKey);

        //apiKey = "AIzaSyD4fYnKWaESh2CBYepQOOgjkT-Fh_JQ2GM";
//        apiKey = BuildConfig.apiKey;
        drawerLayout = findViewById(R.id.main_drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        View view1 = navigationView.getHeaderView(0);
        linearLayout2 = view1.findViewById(R.id.linearVertical);
        imageView = findViewById(R.id.imageView2);
        editText = findViewById(R.id.EnterPrompt);
        temp = findViewById(R.id.tempText);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInputFromInputMethod(editText.getWindowToken(),0);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });
        textViewName = linearLayout2.findViewById(R.id.UserName);
        textViewEmail = linearLayout2.findViewById(R.id.UserMail);

        mAuth = FirebaseAuth.getInstance();
        userId = FirebaseAuth.getInstance().getUid();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemID = item.getItemId();
//                if(itemID == R.id.timeTable){
//                    intent = new Intent(getApplicationContext(), timeTable.class);
//                    startActivity(intent);
//                }
                if (itemID == R.id.chat){
                    intent = new Intent(getApplicationContext(), chat.class);
                    startActivity(intent);
                }
                if (itemID == R.id.studySchedule){
                    intent = new Intent(getApplicationContext(), schedule.class);
                    startActivity(intent);
                }
                if (itemID == R.id.toDo){
                    intent = new Intent(getApplicationContext(), toDo.class);
                    startActivity(intent);
                }
                if (itemID == R.id.timer){
                    intent = new Intent(getApplicationContext(), timer.class);
                    startActivity(intent);
                }
                if (itemID == R.id.logout){
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(MainActivity.this, "Sign out Successful", Toast.LENGTH_SHORT).show();
                    intent = new Intent(getApplicationContext(), Launcher.class);
                    startActivity(intent);
                    finish();
                }
                drawerLayout.close();
                return false;
            }
        });

        db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("ID",userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                nametp = document.getString("name");
                                mailtp = document.getString("mail");
                                Log.d("Shiwal2510", "onComplete: "+nametp+"  &  "+mailtp);
                                textViewName.setText(nametp);
                                textViewEmail.setText(mailtp);
                            }
                        }
                        else {
                            Log.w("Shiwal2510", "Error getting documents.", task.getException());
                        }
                    }
                });


        Log.d("RK1208", "onCreate: "+ arrayListU+"&&&&&&&&&"+arrayListG);
        Map<String,Object> chatMap = new HashMap<>();
        chatMap.put("userPrompt",arrayListU);
        chatMap.put("geminiResponse",arrayListG);
        Map<String, Object> chatHistory = new HashMap<>();
        chatHistory.put("ID", userId);
        chatHistory.put("chat",chatMap);
        chatHistory.put("timeStamp", Timestamp.now());
        db.collection("chatHistory").add(chatHistory).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("RKSH1225", "DocumentSnapshot added with ID: " + documentReference.getId()+ "&   "+userId);
                        currentDocumentId = documentReference.getId();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("RKSH1225", "Error adding document", e);
                    }
                });
//         = db.collection("chatHistory").document().getId();


        Log.d("RKSH1225", "onCreate: "+ currentDocumentId);
        PromptSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Prompt = editText.getText().toString();
                editText.setText("");
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                arrayListU.add(Prompt);
                if (!arrayListU.isEmpty()) {
                    temp.setVisibility(View.GONE); // Hide the TextView
                }
                progressBar.setVisibility(View.VISIBLE); // Show progress bar
                Log.d("Shiwal2510", "onClick: Prompt 1st" + Prompt);

                GenerativeModel gm = new GenerativeModel("gemini-1.5-flash", apiKey);
                GenerativeModelFutures model = GenerativeModelFutures.from(gm);
                Content content = new Content.Builder().addText(Prompt).build();
                Executor executor = Executors.newSingleThreadExecutor();
                ListenableFuture<GenerateContentResponse> response = model.generateContent(content);

                Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
                    @Override
                    public void onSuccess(GenerateContentResponse result) {
                        String resultText = result.getText();
                        Log.d("Shiwal2510", "onSuccess: " + resultText);
                        arrayListG.add(resultText);
                        // Update UI on the main thread
                        runOnUiThread(() -> {
                            progressBar.setVisibility(View.GONE); // Hide progress bar
                            adapter = new chatAdapter(context, arrayListU, arrayListG);

                            recyclerView.setAdapter(adapter);
                            recyclerView.scrollToPosition(adapter.getItemCount() - 1);


//                            Log.d("RK1208", "onCreate: "+ arrayListU+"&&&&&&&&&"+arrayListG);
//                            Map<String,Object> chatMap = new HashMap<>();
                            chatMap.put("userPrompt",arrayListU);
                            chatMap.put("geminiResponse",arrayListG);
//                            Map<String, Object> chatHistory = new HashMap<>();
//                            chatHistory.put("ID", userId);
                            chatHistory.put("chat",chatMap);
//                            chatHistory.put("timeStamp", Timestamp.now());
                            db.collection("chatHistory").document(currentDocumentId).set(chatHistory).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d("Shiwal2510", "DocumentSnapshot added with ID: " +  "&   "+userId);
                                }
                            }) .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("Shiwal2510", "Error adding document", e);
                                        }
                                    });
                        });
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        t.printStackTrace();
                        runOnUiThread(() -> {
                            progressBar.setVisibility(View.GONE); // Hide progress bar
                            Toast.makeText(context, "Error occurred while generating response", Toast.LENGTH_SHORT).show();
                        });
                    }
                }, executor);
            }
        });


//        Log.d("RK1208", "onCreate: "+ arrayListU+"&&&&&&&&&"+arrayListG);
//        Map<String,Object> chatMap = new HashMap<>();
//        chatMap.put("userPrompt",arrayListU);
//        chatMap.put("geminiResponse",arrayListG);
//
//        Map<String, Object> chatHistory = new HashMap<>();
//        chatHistory.put("ID", userId);
//        chatHistory.put("chat",chatMap);
//        db.collection("chatHistory").add(chatHistory).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//            @Override
//            public void onSuccess(DocumentReference documentReference) {
//                Log.d("Shiwal2510", "DocumentSnapshot added with ID: " + documentReference.getId()+ "&   "+userId);
//            }
//        })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("Shiwal2510", "Error adding document", e);
//                    }
//                });


    }
}