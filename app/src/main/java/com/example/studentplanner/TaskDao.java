package com.example.studentplanner;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_table")
class Task {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String description;
    private long deadline;
    private int priority;
    private String subject;
    private boolean isCompleted;

    public Task() {}

    public Task(String title, String description, long deadline, int priority, String subject) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.subject = subject;
        this.isCompleted = false;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public long getDeadline() { return deadline; }
    public int getPriority() { return priority; }
    public String getSubject() { return subject; }
    public boolean isCompleted() { return isCompleted; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setDeadline(long deadline) { this.deadline = deadline; }
    public void setPriority(int priority) { this.priority = priority; }
    public void setSubject(String subject) { this.subject = subject; }
    public void setCompleted(boolean completed) { this.isCompleted = completed; }
}
