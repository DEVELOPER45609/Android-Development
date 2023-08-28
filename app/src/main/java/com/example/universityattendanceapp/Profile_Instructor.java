package com.example.universityattendanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Profile_Instructor extends AppCompatActivity {
    private Button move,move1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_instructor);
        String userEmail = getIntent().getStringExtra("userEmail");


        // Find the TextView where you want to display the email
        TextView userEmailTextView = findViewById(R.id.userEmailTextView);

        // Set the retrieved email to the TextView
        userEmailTextView.setText(userEmail);

        move = findViewById(R.id.manageCoursesButton);

        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Profile_Instructor.this, M_Courses.class);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });

        move1 = findViewById(R.id.manageRosterButton);

        move1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Profile_Instructor.this, M_Rosters.class);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });

    }
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(Profile_Instructor.this, MainActivity.class));
        finish();
    }
}