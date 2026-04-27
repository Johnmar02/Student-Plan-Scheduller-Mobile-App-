package com.example.studentplanner;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {

    private TextInputEditText etTitle, etSubject, etDesc;
    private RadioGroup radioGroup;
    private Button btnDate, btnSave;
    private final Calendar calendar = Calendar.getInstance();
    private long selectedDeadline = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        etTitle = findViewById(R.id.etTitle);
        etSubject = findViewById(R.id.etSubject);
        etDesc = findViewById(R.id.etDesc);
        radioGroup = findViewById(R.id.radioGroupPriority);
        btnDate = findViewById(R.id.btnDate);
        btnSave = findViewById(R.id.btnSave);
        radioGroup.check(R.id.rbLow);

        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, day) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            updateDateButton();
        };

        btnDate.setOnClickListener(v -> new DatePickerDialog(
                AddTaskActivity.this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show());

        btnSave.setOnClickListener(v -> saveTask());
    }

    private void updateDateButton() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        btnDate.setText("Due: " + sdf.format(calendar.getTime()));
        selectedDeadline = calendar.getTimeInMillis();
    }

    private void saveTask() {
        String title = etTitle.getText().toString().trim();
        String subject = etSubject.getText().toString().trim();
        String desc = etDesc.getText().toString().trim();

        if (title.isEmpty() || selectedDeadline == 0) {
            Toast.makeText(this, "Title and Due Date required.", Toast.LENGTH_SHORT).show();
            return;
        }

        int priority = 3; // Low
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.rbHigh) priority = 1;
        else if (selectedId == R.id.rbMed) priority = 2;

        Task task = new Task(title, desc, selectedDeadline, priority, subject);
        AppDatabase.getDatabase(getApplicationContext()).taskDao().insert(task);

        NotificationScheduler.scheduleNotification(this, task);
        Toast.makeText(this, "Task Saved & Reminder Set!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
