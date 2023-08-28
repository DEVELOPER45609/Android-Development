package com.example.universityattendanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class M_Rosters extends AppCompatActivity {

    private ListView courseListView;
    private ArrayList<String> courses;

    private String[] predefinedCourseNames = {
            "Alice Johnson",
            "Bob Smith",
            "Eva Davis",
            "John Doe",
            "Linda Wilson",
            "Michael Brown",
            // Add more course names as needed
    };

    private int currentCourseNameIndex = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_rosters);
        String userEmail = getIntent().getStringExtra("userEmail");


        // Find the TextView where you want to display the email
        TextView userEmailTextView = findViewById(R.id.userEmailTextView);

        // Set the retrieved email to the TextView
        userEmailTextView.setText(userEmail);

        // Initialize the ListView
        courseListView = findViewById(R.id.courseListView);

        // Initialize the ArrayList to store course names
        courses = new ArrayList<>();

        // Create an ArrayAdapter to display the courses in the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(M_Rosters.this,
                android.R.layout.simple_list_item_1, courses);

        // Set the adapter to the ListView
        courseListView.setAdapter(adapter);

        // Set item click listener for the course list
        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle course item click (e.g., remove course)
                String selectedCourse = courses.get(position);
                courses.remove(position);
                adapter.notifyDataSetChanged();
            }
        });

        Button addCourseButton = findViewById(R.id.addCourseButton);
        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add a new course to the ArrayList and update the adapter
                String newCourseName = generateNewCourseName();
                courses.add(newCourseName);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private String generateNewCourseName() {
        String newCourseName = predefinedCourseNames[currentCourseNameIndex];
        currentCourseNameIndex = (currentCourseNameIndex + 1) % predefinedCourseNames.length;
        return newCourseName;
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(M_Rosters.this, MainActivity.class));
        finish();
    }
}




