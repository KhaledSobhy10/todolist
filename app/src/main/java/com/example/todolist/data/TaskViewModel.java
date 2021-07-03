package com.example.todolist.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private LiveData<List<Task>> listLiveData;
    private final AppRepository appRepository;


    public TaskViewModel(@NonNull Application application) {
        super(application);
        appRepository = AppRepository.getInstance(application);
    }


    public LiveData<List<Task>> getTasksByDate(String date) {
        if (listLiveData == null) {
            listLiveData = appRepository.getTasksByDate(date);
        }
        return listLiveData;
    }

    public void updateTask(Task task) {
        appRepository.updateTaskInDB(task);
    }

    public void deleteTaskById(int taskId) {
        appRepository.deleteTaskById(taskId);
    }

    public void deleteAllDataInDB() {
        appRepository.deleteAllDataInDB();
    }

    public void loadDailyTasks() {
        appRepository.loadDailyTasksToTasks();
    }
}
