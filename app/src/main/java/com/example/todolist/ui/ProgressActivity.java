package com.example.todolist.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.R;
import com.example.todolist.Utilities.AppExecutor;
import com.example.todolist.Utilities.DateUtility;
import com.example.todolist.data.AppRepository;
import com.example.todolist.data.TaskProgress;
import com.example.todolist.data.TasksContract;
import com.example.todolist.databinding.ActivityProgressBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class ProgressActivity extends AppCompatActivity {
    private ActivityProgressBinding binding;
    private final static String TAG = ProgressActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProgressBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

       final String taskName = getIntent().getStringExtra("task_name");

        binding.cv.setOnDateChangedListener((widget, date, selected) -> {
            String dateFormatted =date.getDate().format(DateTimeFormatter.ofPattern(DateUtility.datePattern));
            Log.d(TAG, "run: date = "+dateFormatted);
            AppRepository.getInstance(getApplication())
                    .updateThatTaskStatusByDate(taskName, dateFormatted);

        });


        binding.taskNameTv.setText(taskName);
        AppRepository.getInstance(getApplication()).getTaskByName(taskName)
                .observe(this,(taskProgressList)-> AppExecutor.getInstance().getBackgroundThread().execute(() -> {
                    HashSet<CalendarDay> calendarDaysDone = new HashSet<>();
                    HashSet<CalendarDay> calendarDaysUnDone = new HashSet<>();

                    for (TaskProgress taskProgress : taskProgressList){
                        CalendarDay calendarDay = CalendarDay.from(LocalDate.parse(taskProgress.getTaskDate(), DateTimeFormatter.ofPattern(DateUtility.datePattern)));
                        if (taskProgress.getTaskCurrentStatus() == TasksContract.TaskEntity.TASK_DONE)
                            calendarDaysDone.add(calendarDay);
                        else
                            calendarDaysUnDone.add(calendarDay);
                    }
                    runOnUiThread(() -> {
                        binding.taskDoneProTv.setText(getString(R.string.done_num,calendarDaysDone.size()));
                        binding.taskUndoneProTv.setText(getString(R.string.undone_num,calendarDaysUnDone.size()));
                        binding.cv.addDecorator(new DayDecorator(Color.GREEN, calendarDaysDone));
                        binding.cv.addDecorator(new DayDecorator(Color.RED, calendarDaysUnDone));
                    });
                }));
    }



}