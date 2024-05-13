package com.example.myapplication.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.TaskAdapter;
import com.example.myapplication.db.DBHelper;
import com.example.myapplication.model.Task;

import java.util.ArrayList;

public class TaskList extends AppCompatActivity {

    TextView tlView;
    RecyclerView recyclerView;
    TaskAdapter taskAdapter;
    ArrayList<Task> tasks;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        tlView = findViewById(R.id.tlView);

        DBHelper dbHelper;
        dbHelper = new DBHelper(this);


    }

    @Override
    protected void onStart() {
        super.onStart();

        DBHelper dbHelper = null;
        
        tasks = dbHelper.getAllTasks();
        tlView.setText("Total List : " + tasks.size());

        taskAdapter = new TaskAdapter(tasks, this);
        recyclerView.setAdapter(taskAdapter);
        LinearLayoutManager lim = new LinearLayoutManager(this);
        lim.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(lim);
    }
}