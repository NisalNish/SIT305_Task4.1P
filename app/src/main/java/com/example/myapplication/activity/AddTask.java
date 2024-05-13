package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import com.example.myapplication.db.DBHelper;
import com.example.myapplication.model.Task;

public class AddTask extends AppCompatActivity {

    EditText edTitle, edDescription, edDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_task);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edTitle = findViewById(R.id.edTitle);
        edDescription = findViewById(R.id.edDescription);
        edDate = findViewById(R.id.edDate);
    }


    public void viewlist(View view)
    {
        startActivity(new Intent(AddTask.this, TaskList.class));
    }

    public void save(View view) {
        String title = edTitle.getText().toString().trim();
        String description = edDescription.getText().toString().trim();
        String date = edDate.getText().toString().trim();

        DBHelper dbhelper = new DBHelper(AddTask.this);

        Task t = new Task(title, description, date );
        long result = dbhelper.addTask(t);

        if(result > 0)
        {
            Toast.makeText(this, "saved" + result, Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Failed" + result, Toast.LENGTH_SHORT).show();
        }
    }

}