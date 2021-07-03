package com.example.todolist.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Task.class, DailyTask.class,WeeklyTask.class}, version = 4, exportSchema = false)
public abstract class AppRoomDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "task_database";
    private static final String TAG = AppRoomDatabase.class.getSimpleName();
    private static volatile AppRoomDatabase INSTANCE;
    private static final Migration migration_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            String tableName = TasksContract.TaskEntity.TABLE_NAME;
            String newColumn = TasksContract.TaskEntity.TASK_DESCRIPTION;
            database.execSQL("ALTER TABLE " + tableName + " ADD " + newColumn + " TEXT ");
            Log.d(TAG, "migrate: adding new column in task :"+newColumn);

            String tableName2 = TasksContract.DailyTasks.TABLE_NAME;
            String newColumn2 = TasksContract.DailyTasks.COLUMN_DAILY_TASK_DESCRIPTION;
            database.execSQL("ALTER TABLE " + tableName2 + " ADD " + newColumn2 + " TEXT ");
            Log.d(TAG, "migrate: adding new column in daily :"+newColumn2);

        }
    };
    private static final Migration migration_2_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            String tableName = TasksContract.TaskEntity.TABLE_NAME;
            String newColumn = TasksContract.TaskEntity.TASK_PRIORITY;
            database.execSQL("ALTER TABLE " + tableName + " ADD " + newColumn + " INTEGER NOT NULL DEFAULT 1");
            Log.d(TAG, "migrate: adding new column in task : "+newColumn);

            String tableName2 = TasksContract.DailyTasks.TABLE_NAME;
            String newColumn2 = TasksContract.DailyTasks.COLUMN_DAILY_TASK_PRIORITY;
            database.execSQL("ALTER TABLE " + tableName2 + " ADD " + newColumn2 + " INTEGER NOT NULL DEFAULT 1");
            Log.d(TAG, "migrate: adding new column in daily : "+newColumn2);
        }
    };
    private static final Migration migration_3_4 = new Migration(3,4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
              database.execSQL("CREATE TABLE WeeklyTask (id INTEGER PRIMARY KEY NOT NULL, taskName TEXT , description TEXT , day TEXT ,category INTEGER NOT NULL, priority INTEGER NOT NULL)");
        }
    };
    public static AppRoomDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppRoomDatabase.class, DATABASE_NAME)
                            .addMigrations(migration_1_2,migration_2_3,migration_3_4)
                            .build();
                    Log.d(TAG, "getInstance: created");
                }
            }
        }
        return INSTANCE;
    }

    public abstract TasksDao TaskDAO();
}
