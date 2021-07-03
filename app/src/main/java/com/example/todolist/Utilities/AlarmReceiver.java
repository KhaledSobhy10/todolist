package com.example.todolist.Utilities;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.todolist.data.AppRepository;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        AppRepository.getInstance((Application) context.getApplicationContext()).loadDailyTasksToTasks();
    }
}
