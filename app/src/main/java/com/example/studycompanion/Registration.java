package com.example.studycompanion;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {
    EditText regEmail;
    EditText regPass;
    EditText regName;
    Button regButton;
    FirebaseAuth mAuth;
    String userId;
    Intent intent;
    FirebaseFirestore db;
    public int validateEmail(String value){
        String pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(value);
        if (!matcher.matches()) {
            Toast.makeText(this, "Enter a valid Email", Toast.LENGTH_SHORT).show();
            return 0;
        }
        else{
            return 1;
        }
    }

    public int validatePassword(String value) {
        String ans = "";
        // Check if the password is at least 8 characters long
        if (value.length() < 8) {
            ans = "Password must be at least 8 characters long";
        }
        // Check if the password contains at least one uppercase letter
        else if (!value.matches(".*[A-Z].*")) {
            ans = "Password must contain at least one uppercase letter";
        }
        // Check if the password contains at least one lowercase letter
        else if (!value.matches(".*[a-z].*")) {
            ans = "Password must contain at least one lowercase letter";
        }
        // Check if the password contains at least one digit
        else if (!value.matches(".*[0-9].*")) {
            ans = "Password must contain at least one digit";
        }
        // Check if the password contains at least one special character
        else if (!value.matches(".*[!@#\\$&*~].*")) {
            ans = "Password must contain at least one special character";
        }

        if(ans.equals("")){
            return 1;
        }
        else{
            Toast.makeText(this,ans, Toast.LENGTH_SHORT).show();
            return 0;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        regEmail = findViewById(R.id.reg_mail);
        regPass = findViewById(R.id.reg_password);
        regName = findViewById(R.id.reg_name);
        regButton = findViewById(R.id.reg_button);
        intent = new Intent(this, Login.class);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail ;
                String name ;
                String pass ;
                int m = 0;
                int p = 0;
                int n = 1;
                mail = regEmail.getText().toString();
                pass = regPass.getText().toString();
                name = regName.getText().toString();
                m = validateEmail(mail);
                p = validatePassword(pass);
                if(name.isEmpty()){
                    Toast.makeText(Registration.this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
                    n = 0 ;
                }
                if (m == 1 && p == 1 && n == 1) {

                    mAuth.createUserWithEmailAndPassword(mail, pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        // Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser users = mAuth.getCurrentUser();
                                        Toast.makeText(Registration.this, "Account Created", Toast.LENGTH_SHORT).show();
//                                    try {
//                                        wait(150);
//                                    } catch (InterruptedException e) {
//                                        throw new RuntimeException(e);
//                                    }

                                        userId = FirebaseAuth.getInstance().getUid();




                                        Map<String, Object> user = new HashMap<>();
                                        user.put("mail", mail);
                                        user.put("name", name);
                                        user.put("ID", userId);
// Add a new document with a generated ID
                                        db.collection("users")
                                                .add(user)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        Log.d("Shiwal2510", "DocumentSnapshot added with ID: " + documentReference.getId()+ "&   "+userId);
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w("Shiwal2510", "Error adding document", e);
                                                    }
                                                });





                                        startActivity(intent);
                                        finish();

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(Registration.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                }
            }
            });







    }
}
