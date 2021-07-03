package com.example.todolist.data;

import android.provider.BaseColumns;

public class TasksContract {

    public class TaskEntity implements BaseColumns {
        // table name
        public static final String TABLE_NAME = "Tasks";

        // columns in table (tasks)

        // ID of task
        public static final String TASK_ID = "_id";

        // Name of task
        public static final String TASK_NAME = "task_name";

        public static final String TASK_DESCRIPTION = "description";

        // Status if task done or not
        public static final String TASK_STATUS = "status";

        // Category is {working,study,family,health,training}
        public static final String TASK_CATEGORY = "category";

        // Repetition of task {none ,daily,weekly}
        public static final String TASK_REPETITION = "repetition";

        // Priority of task (high (3), medium (2), low (1))
        public static final String TASK_PRIORITY = "priority";


        // Date of task
        public static final String TASK_DATE = "date";

        // needed constants

        public static final int TASK_UNDONE = 0;
        public static final int TASK_DONE = 1;

        public static final int TASK_CATEGORY_WORKING = 1;
        public static final int TASK_CATEGORY_STUDY = 2;
        public static final int TASK_CATEGORY_TRAINING = 3;
        public static final int TASK_CATEGORY_HEALTH = 4;
        public static final int TASK_CATEGORY_FAMILY = 5;

        public static final int TASK_NONE_REPETITION = 0;
        public static final int DAILY_TASK = 1;
        public static final int WEEKLY_TASK = 2;

        public static final int TASK_PRIORITY_HIGH = 3;
        public static final int TASK_PRIORITY_MEDIUM = 2;
        public static final int TASK_PRIORITY_LOW = 1;

    }

    public class DailyTasks implements BaseColumns {

        public static final String TABLE_NAME = "daily_tasks";

        public static final String COLUMN_DAILY_TASK_NAME = "daily_name";
        public static final String COLUMN_DAILY_TASK_CATEGORY = "daily_category";
        public static final String COLUMN_DAILY_TASK_DESCRIPTION = "description";
        public static final String COLUMN_DAILY_TASK_PRIORITY = "daily_priority";

    }

}
