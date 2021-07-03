package com.example.todolist.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.todolist.R;
import com.example.todolist.Utilities.AppExecutor;
import com.example.todolist.data.AppRoomDatabase;
import com.example.todolist.databinding.ActivityProgressBinding;
import com.example.todolist.databinding.ActivityTestBinding;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class testActivity extends AppCompatActivity {

    private ActivityTestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        testCircular();
    }

    private void testCircular() {

AppExecutor.getInstance().getDiskIO().execute(()->{
    double completedTasks = AppRoomDatabase.getInstance(testActivity.this).TaskDAO().numOfCompletedTasks().size();
    double incompleteTasks = AppRoomDatabase.getInstance(testActivity.this).TaskDAO().numOfIncompleteTasks().size();
    runOnUiThread(()->{
        double completedPercent = completedTasks / (completedTasks+incompleteTasks) * 100;
        binding.testPb.setMax((int) (incompleteTasks + completedTasks));
        binding.testPb.setProgress((int) completedTasks);
        binding.percentTv.setText(getString(R.string.completed_percent,(int)completedPercent));
        binding.allTasks.setText(getString(R.string.all_tasks_status,(int)completedTasks,(int)(incompleteTasks + completedTasks)));
//        binding.allTasks.setText("Tasks: "+completedTasks +"/"+(incompleteTasks + completedTasks));
    });
});


    }

//    private void testPieChart() {
//        AppExecutor.getInstance().getDiskIO().execute(new Runnable() {
//            @Override
//            public void run() {
//              float completedTasks =  AppRoomDatabase.getInstance(testActivity.this).TaskDAO().numOfCompletedTasks().size();
//                float incompleteTasks =  AppRoomDatabase.getInstance(testActivity.this).TaskDAO().numOfIncompleteTasks().size();
//
//                runOnUiThread(new Runnable() {
//                  @Override
//                  public void run() {
//                      List<PieEntry> entries = new ArrayList<>();
//                      entries.add(new PieEntry(completedTasks, "Completed"));
//                      entries.add(new PieEntry(incompleteTasks, "Incomplete"));
//                      PieDataSet set = new PieDataSet(entries, "Tasks status");
//                      set.setValueLineColor(Color.YELLOW);
//
//                      List<Integer> colors = new ArrayList<>();
//                      colors.add(Color.GREEN);
//                      colors.add(Color.RED);
//                      set.setColors(colors);
//
//                      PieData data = new PieData(set);
//                      data.setValueTextColor(Color.WHITE);
//                      data.setValueTextSize(24);
//
//                      binding.testPieChart.setData(data);
//                      binding.testPieChart.setCenterText("Tasks");
//
//                  }
//              });
//
//
//            }
//        });
//
//
//
//    }


}