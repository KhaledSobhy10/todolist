package com.example.todolist.Utilities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class Alarms {

    private static final int ADDING_DAILY_TASK = 129093;

    public static void addingDailyTasksAlarm(Context context) {
        Intent intent = new Intent(context, AlarmReceiver.class);

        if (PendingIntent.getBroadcast(context, ADDING_DAILY_TASK, intent, PendingIntent.FLAG_NO_CREATE) == null) {

            //Create an alarm manager
            AlarmManager mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            //Create intent will fire alarm receiver
            //Pending that will go to alarm app
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ADDING_DAILY_TASK, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            //setup time of behavior
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0); // 12 A.M == 00:00
            calendar.set(Calendar.MINUTE, 0);

            mAlarmManager.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        }

    }
}
