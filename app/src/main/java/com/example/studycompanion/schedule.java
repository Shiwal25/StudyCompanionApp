package com.example.studycompanion;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ScrollView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class schedule extends AppCompatActivity {
    ImageButton imageButton;
    ImageButton imageButton2;
    String startTime, endTime, name;
    ArrayList<Integer> dayCheck;
    ScrollView scrollView1, scrollView2;
    RecyclerView recyclerView;
    ArrayList<String> arrayList;
    scheduleAdapter adapter;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_schedule);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imageButton = findViewById(R.id.back_todo);
        imageButton2 = findViewById(R.id.addTask);
        name = getIntent().getStringExtra("name");
        startTime = getIntent().getStringExtra("startstr");
        endTime = getIntent().getStringExtra("endstr");
        dayCheck = new ArrayList<>();
        dayCheck = getIntent().getIntegerArrayListExtra("dayCheck");
        scrollView1 = findViewById(R.id.timescrollView);
        scrollView2 = findViewById(R.id.taskscrollView);
        recyclerView = findViewById(R.id.taskRV);
        arrayList = new ArrayList<>();
        context = getApplicationContext();
        arrayList.add(name);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new scheduleAdapter(this, arrayList);
        recyclerView.setAdapter(adapter);

        scrollView1.getViewTreeObserver().addOnScrollChangedListener(() -> {
            int scrollY = scrollView1.getScrollY();
            scrollView2.scrollTo(0, scrollY);
        });

        scrollView2.getViewTreeObserver().addOnScrollChangedListener(() -> {
            int scrollY = scrollView2.getScrollY();
            scrollView1.scrollTo(0, scrollY);
        });

        Log.d("RK1225", "onCreate: " + name + startTime + endTime + dayCheck);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment();
            }
        });



    }

    private void openFragment() {
        // Create a new instance of your fragment (e.g., addTask)
        addTask fragment = new addTask();

        // Begin a FragmentTransaction to add or replace the fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Optionally, you can add the fragment to the back stack to allow for navigation back
        transaction.replace(R.id.fragment_container, fragment); // Replace with your container's ID
        transaction.addToBackStack(null); // This will allow the user to press back to return to the previous screen

        // Commit the transaction to apply the changes
        transaction.commit();
    }
}