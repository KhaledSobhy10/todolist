package com.example.todolist.data;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.todolist.R;

@Entity(tableName = TasksContract.TaskEntity.TABLE_NAME)
public class Task implements Parcelable {

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
    /**
     * id of task
     */
    @ColumnInfo(name = TasksContract.TaskEntity.TASK_ID)
    @PrimaryKey(autoGenerate = true)
    private int taskId;
    /**
     * name of task
     */

    @ColumnInfo(name = TasksContract.TaskEntity.TASK_NAME)
    @NonNull
    private String taskName;
    @ColumnInfo
    @Nullable
    private String description;
    /**
     * status of task , done or not
     */
    @ColumnInfo(name = TasksContract.TaskEntity.TASK_STATUS, defaultValue = "0")
    private int status;
    /**
     * repetition of task
     */
    @ColumnInfo(name = TasksContract.TaskEntity.TASK_REPETITION)
    private int repetition;
    /**
     * category of task
     */
    @ColumnInfo(name = TasksContract.TaskEntity.TASK_CATEGORY)
    private int category;
    /**
     * date of task
     */
    @NonNull
    @ColumnInfo(name = TasksContract.TaskEntity.TASK_DATE)
    private String date;

    public int getPriority() {
        return priority;
    }

    @ColumnInfo(name = TasksContract.TaskEntity.TASK_PRIORITY, defaultValue = "1")
    private int priority;


    @Ignore
    public Task(String taskName, int repetition, int category) {
        this.taskName = taskName;
        this.repetition = repetition;
        this.category = category;
    }

    public Task(String taskName, int status, int repetition, int category, String date, String description, int priority) {
        this.taskName = taskName;
        this.status = status;
        this.repetition = repetition;
        this.category = category;
        this.date = date;
        this.description = description;
        this.priority = priority;
    }
  


    @Ignore
    public Task(int taskId, String taskName, int status, int repetition, int category, String date) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.status = status;
        this.repetition = repetition;
        this.category = category;
        this.date = date;
    }


    protected Task(Parcel in) {
        taskId = in.readInt();
        taskName = in.readString();
        description = in.readString();
        status = in.readInt();
        repetition = in.readInt();
        category = in.readInt();
        date = in.readString();
        priority = in.readInt();
    }

    @Ignore
    public static int convertCategoryToInt(String category) {
        switch (category) {
            case "working":
                return TasksContract.TaskEntity.TASK_CATEGORY_WORKING;
            case "training":
                return TasksContract.TaskEntity.TASK_CATEGORY_TRAINING;
            case "family":
                return TasksContract.TaskEntity.TASK_CATEGORY_FAMILY;
            case "health":
                return TasksContract.TaskEntity.TASK_CATEGORY_HEALTH;
            default:
                return TasksContract.TaskEntity.TASK_CATEGORY_STUDY;
        }
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int i) {
        status = i;
    }

    public int getRepetition() {
        return repetition;
    }

    public void setRepetition(int repetition) {
        this.repetition = repetition;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Ignore
    public String getStringRepetition() {
        switch (repetition) {
            case TasksContract.TaskEntity.DAILY_TASK:
                return "daily";
            case TasksContract.TaskEntity.WEEKLY_TASK:
                return "weekly";
            default:
                return "once";
        }
    }

    @Ignore
    public String getStringCategory() {
        switch (category) {
            case TasksContract.TaskEntity.TASK_CATEGORY_WORKING:
                return "working";
            case TasksContract.TaskEntity.TASK_CATEGORY_TRAINING:
                return "training";
            case TasksContract.TaskEntity.TASK_CATEGORY_FAMILY:
                return "family";
            case TasksContract.TaskEntity.TASK_CATEGORY_HEALTH:
                return "health";
            default:
                return "studying";

        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(taskId);
        dest.writeString(taskName);
        dest.writeString(description);
        dest.writeInt(status);
        dest.writeInt(repetition);
        dest.writeInt(category);
        dest.writeString(date);
        dest.writeInt(priority);
    }

    public int getColorRGBForPriority(Resources resources) {

        int colorRes = R.color.colorLowPriority;
        switch (priority) {
            case TasksContract.TaskEntity.TASK_PRIORITY_MEDIUM:
                colorRes = R.color.colorMediumPriority;
                break;
            case TasksContract.TaskEntity.TASK_PRIORITY_HIGH:
                colorRes = R.color.colorHighPriority;
                break;
        }
        return ResourcesCompat.getColor(resources, colorRes, null); //without theme
    }
}
