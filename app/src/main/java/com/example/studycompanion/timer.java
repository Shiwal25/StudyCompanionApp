package com.example.studycompanion;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class timer extends AppCompatActivity {

    TextView textView;
    EditText editText1,editText2, editText3;
    ImageButton button,button2;
    int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_timer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        button = findViewById(R.id.button2);
        Log.d("RKSH1208", "onCreate: CRASHED 1");
        editText1 = findViewById(R.id.hoursTextView);
        editText2 = findViewById(R.id.minutesTextView);
        editText3 = findViewById(R.id.secondsTextView);
        textView = findViewById(R.id.timerText);
        button2 = findViewById(R.id.back_todo);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hour = editText1.getText().toString();
                int h = Integer.parseInt(hour);
                String minutes = editText2.getText().toString();
                int m = Integer.parseInt(minutes);
                String second = editText3.getText().toString();
                int s = Integer.parseInt(second);
                time = (h*3600000 + m*60000 + s*1000);

                Log.d("RKSH1208", "onCreate: " + time);
                new CountDownTimer(time, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        // Used for formatting digit to be in 2 digits only
                        NumberFormat f = new DecimalFormat("00");
                        long hour = (millisUntilFinished / 3600000) % 24;
                        long min = (millisUntilFinished / 60000) % 60;
                        long sec = (millisUntilFinished / 1000) % 60;
                        textView.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
                    }
                    // When the task is over it will print 00:00:00 there
                    public void onFinish() {
                        Uri alarmSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                        textView.setText("00:00:00");
                        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), alarmSoundUri);
                        mediaPlayer.start();
                        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                            if (mediaPlayer.isPlaying()) {
                                mediaPlayer.stop();
                                mediaPlayer.release();
                            }
                        }, 5000); // 5000 milliseconds = 5 seconds
                    }
                }.start();
                editText1.setText("00");
                editText2.setText("00");
                editText3.setText("01");
            }
        });



    }
}