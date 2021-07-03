package com.example.todolist.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = TasksContract.DailyTasks.TABLE_NAME)
public class DailyTask {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = TasksContract.DailyTasks.COLUMN_DAILY_TASK_NAME)
    String dailyTasksName;

    @ColumnInfo
    @Nullable
    String description;

    @ColumnInfo(name = TasksContract.DailyTasks.COLUMN_DAILY_TASK_CATEGORY)
    String category;


    @ColumnInfo(name = TasksContract.DailyTasks.COLUMN_DAILY_TASK_PRIORITY,defaultValue = "1")
    private int priority;

    public DailyTask(String dailyTasksName, String category, String description,int priority) {
        this.dailyTasksName = dailyTasksName;
        this.category = category;
        this.description = description;
        this.priority = priority;
    }

    public String getDailyTasksName() {
        return dailyTasksName;
    }

    public void setDailyTasksName(String dailyTasksName) {
        this.dailyTasksName = dailyTasksName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }
}
