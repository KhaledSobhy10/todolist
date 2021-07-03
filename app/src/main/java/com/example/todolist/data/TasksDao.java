package com.example.todolist.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todolist.Utilities.DateUtility;

import java.util.List;

@Dao
public interface TasksDao {

    @Insert
    void insertTasks(Task... task);   // can insert one or more task from task table

    @Update
    void updateTasks(Task... task);  // can update one or more task from task table

    @Query("SELECT status , date FROM TASKS WHERE task_name = :taskName")
    LiveData<List<TaskProgress>> getTaskByName(String taskName);

  @Query("SELECT status FROM TASKS WHERE task_name = :taskName AND date = :date")
  Integer getStatusOfTask(String taskName,String date);

  @Query("UPDATE Tasks SET status = :newStatus WHERE task_name = :taskName AND date = :date")
  void updateStatusOfTaskInThatDate(String taskName , String date,int newStatus);



    @Query("SELECT * FROM Tasks WHERE date = :date ORDER BY priority DESC , status ASC")
    LiveData<List<Task>> getTasksByDate(String date);

    @Query("SELECT _id FROM Tasks WHERE status  = 1")
    List<Integer> numOfCompletedTasks();

    @Query("SELECT _id FROM Tasks WHERE status  = 0")
    List<Integer> numOfIncompleteTasks();



  @Delete
    void deleteTasks(Task... task);   // can delete one or more task from task table

    @Query("SELECT * FROM TASKS WHERE _id = :id")
    LiveData<Task> getTaskById(int id);

    @Query("DELETE FROM TASKS")
    void deleteAllTasks();

    @Query("DELETE FROM Tasks WHERE _id = :taskId")
    void deleteTaskById(int taskId);


    // daily task

    @Query("SELECT * FROM daily_tasks")
    LiveData<List<DailyTask>> getDailyTasks();

    @Insert
    void insertDailyTask(DailyTask... dailyTasks);

    @Query("DELETE FROM DAILY_TASKS")
    void deleteAllDailyTasks();

    @Update
    void updateDailyTasks(DailyTask... dailyTasks);

    //No liveData
    @Query("SELECT * FROM daily_tasks")
    List<DailyTask> getDailyTasksList();

//    @Query("SELECT task_name FROM Tasks WHERE repetition = 1 and date = :todayDate")
//    List <String> getTodayDailyTask(String todayDate);

    @Query("SELECT task_name FROM Tasks WHERE task_name = :taskName and date = :todayDate")
    String haveThatDailyTask(String taskName , String todayDate);

    @Query("SELECT task_name FROM Tasks WHERE _id = :taskId")
    String getTaskNameById(int taskId);

    @Query("DELETE FROM daily_tasks WHERE daily_name = :taskName")
    void deleteDailyTaskByName(String taskName);
}
