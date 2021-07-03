package com.example.todolist.data;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.todolist.Utilities.AppExecutor;

import java.util.List;

import static com.example.todolist.Utilities.DateUtility.getCurrentDate;

public class AppRepository {
    private static final String TAG = "MY";
    private static AppRepository appRepository;
    private static AppRoomDatabase appRoomDatabase;


    private AppRepository() {
    }

    public static AppRepository getInstance(Application application) {
        if (appRepository == null) {
            synchronized (AppRepository.class) {
                if (appRepository == null) {
                    Log.d(TAG, "getInstance: make new App rep");
                    appRepository = new AppRepository();
                    appRoomDatabase = AppRoomDatabase.getInstance(application);
                }
            }

        }
        return appRepository;
    }


    public void insertTask(Task task) {
        AppExecutor.getInstance().getDiskIO().execute(() -> {
            if (task.getRepetition() == TasksContract.TaskEntity.DAILY_TASK) {
                appRoomDatabase.TaskDAO().insertDailyTask(new DailyTask(task.getTaskName(), task.getStringCategory(), task.getDescription(), task.getPriority()));
            }else if (task.getRepetition() == TasksContract.TaskEntity.WEEKLY_TASK){

            }
            appRoomDatabase.TaskDAO().insertTasks(task);
        });

    }


    public void updateTaskInDB(Task task) {
        AppExecutor.getInstance().getDiskIO().execute(() -> {
            appRoomDatabase.TaskDAO().updateTasks(task);
            if (task.getRepetition() == TasksContract.TaskEntity.DAILY_TASK)
                appRoomDatabase.TaskDAO().updateDailyTasks(new DailyTask(task.getTaskName(), task.getStringCategory(), task.getDescription(), task.getPriority()));
        });

    }


    /**
     * @param taskId id of task
     * @return wanted task
     * @implNote method run in called thread , not background thread
     */
    public LiveData<Task> getTaskByIdFromDB(int taskId) {
        return appRoomDatabase.TaskDAO().getTaskById(taskId);

    }


//    private void notifyDataChanged(MySqliteHelper mySqliteHelper) {
//        tasksMutableLiveData.postValue(mySqliteHelper.getTodayTasks());
//    }


    public void deleteAllDataInDB() {
        AppExecutor.getInstance().getDiskIO().execute(() -> {
            appRoomDatabase.TaskDAO().deleteAllTasks();
            appRoomDatabase.TaskDAO().deleteAllDailyTasks();
        });
    }


    public LiveData<List<Task>> getTasksByDate(String date) {
        return appRoomDatabase.TaskDAO().getTasksByDate(date);
    }


    public void deleteTaskById(int taskId) {
        AppExecutor.getInstance().getDiskIO().execute(() -> {

            String taskName = appRoomDatabase.TaskDAO().getTaskNameById(taskId);

           //Delete task from daily task  table
            appRoomDatabase.TaskDAO().deleteDailyTaskByName(taskName);

            //Delete task from task table
            appRoomDatabase.TaskDAO().deleteTaskById(taskId);
        });
    }

    private boolean isThatTaskLoaded(String taskName) {
        String result = appRoomDatabase.TaskDAO().haveThatDailyTask(taskName, getCurrentDate());
        return result != null;
    }

    public LiveData<List<TaskProgress>> getTaskByName(String taskName) {
        return appRoomDatabase.TaskDAO().getTaskByName(taskName);
    }

    public void loadDailyTasksToTasks() {
        AppExecutor.getInstance().getDiskIO().execute(() -> {

            List<DailyTask> dailyTaskList = appRoomDatabase.TaskDAO().getDailyTasksList();

            for (DailyTask dailyTask : dailyTaskList) {

                if (isThatTaskLoaded(dailyTask.getDailyTasksName()))
                    continue;

                int category = Task.convertCategoryToInt(dailyTask.getCategory());

                Task task = new Task(dailyTask.dailyTasksName
                        , TasksContract.TaskEntity.TASK_UNDONE
                        , TasksContract.TaskEntity.DAILY_TASK
                        , category
                        , getCurrentDate()
                        , dailyTask.getDescription()
                        , dailyTask.getPriority()
                );

                appRoomDatabase.TaskDAO().insertTasks(task);
            }
        });
    }

    public void updateThatTaskStatusByDate(String taskName, String date) {
        AppExecutor.getInstance().getDiskIO().execute(() -> {
            Integer currentStatus = appRoomDatabase.TaskDAO().getStatusOfTask(taskName, date);
            if (currentStatus != null) {
                appRoomDatabase.TaskDAO().updateStatusOfTaskInThatDate(taskName, date, currentStatus == TasksContract.TaskEntity.TASK_DONE ?
                        TasksContract.TaskEntity.TASK_UNDONE : TasksContract.TaskEntity.TASK_DONE);
            }
        });
    }
}
