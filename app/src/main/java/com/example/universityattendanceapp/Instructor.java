package com.example.universityattendanceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class Instructor extends AppCompatActivity {
    TextInputEditText editTextEmail, editTextPassword;
    Button buttonLogin;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.instructorEmailEditText1);
        editTextPassword = findViewById(R.id.instructorPasswordEditText1);
        buttonLogin = findViewById(R.id.InstructorSignInButton1);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.registrationButton);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InstructorRegistration.class);
                startActivity(intent);
                finish();

            }
        });


        buttonLogin.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {

                                               progressBar.setVisibility(View.VISIBLE);
                                               String email, password;
                                               email = editTextEmail.getText().toString();
                                               password = editTextPassword.getText().toString();
                                               buttonLogin = findViewById(R.id.InstructorSignInButton1);

                                               if (TextUtils.isEmpty(email)) {
                                                   Toast.makeText(Instructor.this, "Enter email", Toast.LENGTH_SHORT).show();
                                                   progressBar.setVisibility(View.GONE);
                                                   return;
                                               }
                                               if (TextUtils.isEmpty(password)) {
                                                   Toast.makeText(Instructor.this, "Enter password", Toast.LENGTH_SHORT).show();
                                                   progressBar.setVisibility(View.GONE);
                                                   return;
                                               }

                                               mAuth.signInWithEmailAndPassword(email, password)
                                                       .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                           @Override
                                                           public void onComplete(@NonNull Task<AuthResult> task) {
                                                               progressBar.setVisibility(View.GONE);
                                                               if (task.isSuccessful()) {
                                                                   FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                                                   if (firebaseUser != null) {
                                                                       String userEmail = firebaseUser.getEmail();

                                                                       Intent intent = new Intent(Instructor.this, Profile_Instructor.class);
                                                                       intent.putExtra("userEmail", userEmail);
                                                                       startActivity(intent);
                                                                       finish();
                                                                   }
                                                               } else {
                                                                   if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                                                       Toast.makeText(Instructor.this, "Invalid Password",
                                                                               Toast.LENGTH_SHORT).show();
                                                                   } else if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                                                                       Toast.makeText(Instructor.this, "Email not in use",
                                                                               Toast.LENGTH_SHORT).show();
                                                                   }
                                                               }
                                                           }
                                                       });

                                           }



                                       }
        );




        ImageView returnButton = findViewById(R.id.myImageView1);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Instructor.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}