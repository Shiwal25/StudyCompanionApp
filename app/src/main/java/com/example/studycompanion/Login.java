package com.example.studycompanion;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    Button login;
    EditText logMail,logPass;
    TextView lTr;
    FirebaseAuth mAuth;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth = FirebaseAuth.getInstance();
        login = findViewById(R.id.log_button);
        logMail = findViewById(R.id.log_email);
        logPass = findViewById(R.id.log_pass);
        lTr = findViewById(R.id.logToReg);



        lTr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), Registration.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = logMail.getText().toString();

                String pass = logPass.getText().toString();


                if(TextUtils.isEmpty(mail)){
                    Toast.makeText(Login.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    Toast.makeText(Login.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }



                mAuth.signInWithEmailAndPassword(mail, pass)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    //Log.d("loginSuccess", "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    finishAfterTransition();

                                    //updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    // Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }
                            }
                        });

            }
        }));

    }
}