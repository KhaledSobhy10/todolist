package com.example.todolist.Utilities;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutor {
    private static AppExecutor appExecutor;
    private final Executor diskIO;
    private final Executor backgroundThread;

    private AppExecutor(Executor diskIO,Executor backgroundThread) {
        this.backgroundThread = backgroundThread;
        this.diskIO = diskIO;
    }

    public static AppExecutor getInstance() {
        if (appExecutor == null) {
            synchronized (AppExecutor.class) {
                if (appExecutor == null)
                    appExecutor = new AppExecutor(Executors.newSingleThreadExecutor(),Executors.newSingleThreadExecutor());
            }
        }
        return appExecutor;
    }

    public Executor getDiskIO() {
        return diskIO;
    }

    public Executor getBackgroundThread() {
        return backgroundThread;
    }
}
