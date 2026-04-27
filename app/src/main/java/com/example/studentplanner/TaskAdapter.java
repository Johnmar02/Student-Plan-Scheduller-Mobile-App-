package com.example.studentplanner;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
interface TaskDao {
    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Query("SELECT * FROM task_table ORDER BY isCompleted ASC, deadline ASC")
    List<Task> getAllTasks();

    @Query("SELECT COUNT(*) FROM task_table WHERE isCompleted = 1")
    int getCompletedCount();

    @Query("SELECT COUNT(*) FROM task_table")
    int getTotalCount();
}
