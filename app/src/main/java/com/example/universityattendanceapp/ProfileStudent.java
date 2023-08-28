package com.example.universityattendanceapp;

import static androidx.core.content.ContextCompat.startActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ProfileStudent extends AppCompatActivity {

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_student);
        databaseReference = FirebaseDatabase.getInstance().getReference();



        // Fetch courses from Firebase


    // ... Other methods ...


        String userEmail = getIntent().getStringExtra("userEmail");

        // Find the TextView where you want to display the email
        TextView userEmailTextView = findViewById(R.id.userEmailTextView);

        // Set the retrieved email to the TextView
        userEmailTextView.setText(userEmail);

        // ... Other initialization and logic ...
    }

    public void markAttendance(View view) {
        try {
            String userEmail = getIntent().getStringExtra("userEmail");

            // Find the selected course by checking the state of checkboxes
            CheckBox courseCloudComputing = findViewById(R.id.courseCloudComputing);
            CheckBox courseProjectManagement = findViewById(R.id.courseProjectManagement);
            CheckBox courseBusinessAnalytics = findViewById(R.id.courseBusinessAnalytics);
            CheckBox courseMachineLearning = findViewById(R.id.courseMachineLearning);
            CheckBox courseCyberSecurity = findViewById(R.id.courseCyberSecurity);

            String selectedCourse = "";

            if (courseCloudComputing.isChecked()) {
                selectedCourse = "Cloud Computing";
            } else if (courseProjectManagement.isChecked()) {
                selectedCourse = "Project Management";
            } else if (courseBusinessAnalytics.isChecked()) {
                selectedCourse = "Business Analytics";
            } else if (courseMachineLearning.isChecked()) {
                selectedCourse = "Machine Learning";
            } else if (courseCyberSecurity.isChecked()) {
                selectedCourse = "Cyber Security";
            }

            if (!selectedCourse.isEmpty()) {
                // Create a new attendance record
                String attendanceId = databaseReference.child("attendance").push().getKey();
                Attendance attendance = new Attendance(userEmail, selectedCourse);

                // Upload the attendance data to the Realtime Database
                databaseReference.child("attendance").child(attendanceId).setValue(attendance)
                        .addOnSuccessListener(aVoid -> {
                            // Data has been successfully uploaded
                            Toast.makeText(ProfileStudent.this, "Attendance marked!", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            // Handle errors
                            Toast.makeText(ProfileStudent.this, "Error marking attendance", Toast.LENGTH_SHORT).show();
                            Log.e("DatabaseError", "Error marking attendance", e);
                        });

            } else {
                // No course selected, show an error message
                Toast.makeText(this, "Please select a course", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            // Handle exceptions
            Log.e("MarkAttendanceError", "Error marking attendance", e);
            Toast.makeText(ProfileStudent.this, "Error marking attendance", Toast.LENGTH_SHORT).show();
        }
    }

    private static class Attendance {
        private String userEmail;
        private String course;
        private String date;

        public Attendance(String userEmail, String course) {
            this.userEmail = userEmail;
            this.course = course;
            this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        }
        public String getUserEmail() {
            return userEmail;
        }

        public String getCourse() {
            return course;
        }

        public String getDate() {
            return date;
        }

        // If you ever need to set these fields externally, you can add setter methods
        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public void setCourse(String course) {
            this.course = course;
        }
    }




    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(ProfileStudent.this, MainActivity.class));
        finish();
    }

}