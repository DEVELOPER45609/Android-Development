package com.example.universityattendanceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

public class StudentRegistration extends AppCompatActivity {
    TextInputEditText editTextEmail,editTextPassword;
    Button buttonReg;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);
        mAuth=FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.studentEmailEditText1);
        editTextPassword = findViewById(R.id.studentPasswordEditText1);
        buttonReg = findViewById(R.id.registrationButton);
        progressBar=findViewById(R.id.progressBar);
        textView=findViewById(R.id.loginNow);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Student.class);
                startActivity(intent);
                finish();
            }
        });

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(view.VISIBLE);
                String email, password;
                email= editTextEmail.getText().toString();
                password= editTextPassword.getText().toString();

                if(TextUtils.isEmpty(email)){
                  Toast.makeText(StudentRegistration.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                  Toast.makeText(StudentRegistration.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {

                                    FirebaseUser firebaseUser=mAuth.getCurrentUser();


                                    ReadWriteUserDetails writeUserDetails =new ReadWriteUserDetails(email);
                                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
                                    referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            firebaseUser.sendEmailVerification();
                                            Toast.makeText(StudentRegistration.this, "Account Created.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                } else {


                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if(e  instanceof FirebaseAuthUserCollisionException){
                                    Toast.makeText(StudentRegistration.this, "Email Already in use",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

            }
        });


    }
}