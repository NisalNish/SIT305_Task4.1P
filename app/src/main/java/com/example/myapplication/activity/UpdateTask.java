package com.example.myapplication.activity;

import android.annotation.SuppressLint;
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

public class UpdateTask extends AppCompatActivity {

    EditText edTitle, edDescription, edDate;
    int id;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_task);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Task t = (Task) getIntent().getExtras().getSerializable("TASK");

        id = t.getId();
        edTitle = findViewById(R.id.edTitle);
        edDescription = findViewById(R.id.edDescription);
        edDate = findViewById(R.id.edDate);


        edTitle.setText(t.getTitle());
        edDescription.setText(t.getDescription());
        edDate.setText(t.getDate());
    }


    public void Update(View view) {

        String title = edTitle.getText().toString().trim();
        String description = edDescription.getText().toString().trim();
        String date = edDate.getText().toString().trim();

        Task t = new Task(id, title, description, date);
        DBHelper dbHelper = new DBHelper(this);
        int result = dbHelper.updateTask(t);

        if(result > 0)
        {
            Toast.makeText(this, "Updated" + result, Toast.LENGTH_SHORT).show();
            finish();
        }
        else
        {
            Toast.makeText(this, "Failed" + result, Toast.LENGTH_SHORT).show();
        }


    }

}