package com.example.todolist.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WeeklyTask{

    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String taskName;
    private String description;
    private String day;
    private int category;
    private int priority;

    public String getTaskName() {
        return taskName;
    }

    public String getDescription() {
        return description;
    }

    public String getDay() {
        return day;
    }

    public int getCategory() {
        return category;
    }

    public int getPriority() {
        return priority;
    }

    public WeeklyTask(String taskName, String description, String day, int category, int priority) {
        this.taskName = taskName;
        this.description = description;
        this.day = day;
        this.category = category;
        this.priority = priority;
    }
}
