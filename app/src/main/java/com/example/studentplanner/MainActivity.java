package com.example.studentplanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnTaskClickListener {

    private RecyclerView recyclerView;
    private TextView tvProgress;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getDatabase(this);

        tvProgress = findViewById(R.id.tvProgress);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddTaskActivity.class)));

        loadTasks();
    }

    private void loadTasks() {
        List<Task> tasks = db.taskDao().getAllTasks();
        recyclerView.setAdapter(new TaskAdapter(tasks, this));
        updateProgress();
    }

    private void updateProgress() {
        int total = db.taskDao().getTotalCount();
        int completed = db.taskDao().getCompletedCount();
        tvProgress.setText("Productivity: " + completed + "/" + total + " Tasks Completed");
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTasks();
    }

    @Override
    public void onTaskCheck(Task task) {
        db.taskDao().update(task);
        loadTasks();
    }
}
