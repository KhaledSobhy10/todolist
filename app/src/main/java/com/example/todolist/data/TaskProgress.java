package com.example.todolist.data;

import androidx.room.ColumnInfo;

// class to hold needed data for progress activity
public class TaskProgress {

    @ColumnInfo(name =TasksContract.TaskEntity.TASK_DATE)
    private String taskDate;
    @ColumnInfo(name = TasksContract.TaskEntity.TASK_STATUS)
    private int taskCurrentStatus;

    public TaskProgress(String taskDate, int taskCurrentStatus) {
        this.taskDate = taskDate;
        this.taskCurrentStatus = taskCurrentStatus;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public int getTaskCurrentStatus() {
        return taskCurrentStatus;
    }
}
