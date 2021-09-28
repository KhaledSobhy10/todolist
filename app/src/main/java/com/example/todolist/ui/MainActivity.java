package com.example.todolist.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.Utilities.Alarms;
import com.example.todolist.data.Task;
import com.example.todolist.data.TaskViewModel;
import com.example.todolist.data.TasksContract;
import com.example.todolist.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import static com.example.todolist.Utilities.DateUtility.getCurrentDate;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MRecyclerViewAdapter.ItemClickListener, MyDialogFragment.AddingTaskListener {


    public static final String KEY_UPDATE_TASK_ID = "update_task_id";
    private ActivityMainBinding binding;
    private MRecyclerViewAdapter myAdapter;
    private TaskViewModel testViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initViews();

        Alarms.addingDailyTasksAlarm(this);

        setupRecyclerView();

        // Define viewModel and set LiveData
        testViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(TaskViewModel.class);
        testViewModel.getTasksByDate(getCurrentDate()).observe(this, tasks -> {
            myAdapter.setData((ArrayList<Task>) tasks);
            binding.tasksStatusProgressBar.setMax(tasks.size());
            int numOfCompletedTasks = getNumOfCompletedTasks(tasks);
            binding.tasksStatusProgressBar.setProgress(numOfCompletedTasks);
            binding.progressNumTv.setText(getString(R.string.progress_num, numOfCompletedTasks, tasks.size()));

        });


        binding.categoriesChipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            int category;
            if (checkedId == R.id.working_filter_ch) {
                category = TasksContract.TaskEntity.TASK_CATEGORY_WORKING;
            } else if (checkedId == R.id.workingOut_filter_ch) {
                category = TasksContract.TaskEntity.TASK_CATEGORY_TRAINING;
            } else if (checkedId == R.id.family_filter_ch) {
                category = TasksContract.TaskEntity.TASK_CATEGORY_FAMILY;
            } else if (checkedId == R.id.health_filter_ch) {
                category = TasksContract.TaskEntity.TASK_CATEGORY_HEALTH;
            } else if (checkedId == R.id.learning_filter_ch) {
                category = TasksContract.TaskEntity.TASK_CATEGORY_STUDY;
            } else {
                category = 0;
            }
            testViewModel.getTasksByDate(getCurrentDate()).observe(MainActivity.this, (taskList) -> myAdapter.setData(filteringByCategory(taskList, category)));
        });


        binding.bottomAppBar.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.action_delete_all_data) {
                showDeletingAllDataAlertDialog();
                return true;
            } else if (itemId == R.id.action_load_today_task) {
                testViewModel.loadTodayTasks();
                return true;
            } else if (itemId == R.id.action_change_them_mode) {
                setThemeMode(item);
                return true;
            }
            return false;
        });
        binding.bottomAppBar.setNavigationOnClickListener(v -> startActivity(new Intent(MainActivity.this, testActivity.class)));

    }

    private int getNumOfCompletedTasks(List<Task> tasks) {
        int numOfCompletedTasks = 0;
        for (Task task : tasks) {
            if (task.getStatus() == TasksContract.TaskEntity.TASK_DONE)
                numOfCompletedTasks++;
        }
        return numOfCompletedTasks;
    }


    public void initViews() {
        binding.fab.setOnClickListener(this);
        setTextInSwitchModeMenu(binding.bottomAppBar.getMenu().findItem(R.id.action_change_them_mode));
    }

    public void setupRecyclerView() {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MRecyclerViewAdapter(this);
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab) {
            startActivity(new Intent(this, AddTaskActivity.class));
        }
    }


    private void showDeletingAlertDialog(int taskId) {
        MyDialogFragment deletingAlertDialog = new MyDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(MyDialogFragment.KEY_TASK_ID, taskId);
        bundle.putString(MyDialogFragment.KEY_DIALOG_TYPE, MyDialogFragment.DELETE_TASK_ALERT);
        deletingAlertDialog.setArguments(bundle);
        deletingAlertDialog.show(getSupportFragmentManager(), MyDialogFragment.DELETE_TASK_ALERT);
    }

    private void showDeletingAllDataAlertDialog() {
        MyDialogFragment deletingAlertDialog = new MyDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MyDialogFragment.KEY_DIALOG_TYPE, MyDialogFragment.DELETE_ALL_DATA_ALERT);
        deletingAlertDialog.setArguments(bundle);
        deletingAlertDialog.show(getSupportFragmentManager(), MyDialogFragment.DELETE_ALL_DATA_ALERT);
    }

    private void showDetailsDialog(Task task) {
        MyDialogFragment detailsDialog = new MyDialogFragment();
        detailsDialog.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.fit_screen_dialog);
        Bundle bundle = new Bundle();
        bundle.putParcelable("task", task);
        bundle.putString(MyDialogFragment.KEY_DIALOG_TYPE, MyDialogFragment.DETAILS_TASK_DIALOG);
        detailsDialog.setArguments(bundle);
        detailsDialog.show(getSupportFragmentManager(), MyDialogFragment.DETAILS_TASK_DIALOG);
    }


    private ArrayList<Task> filteringByCategory(List<Task> tasks, int category) {
        if (category == 0) return (ArrayList<Task>) tasks;
        ArrayList<Task> taskArrayList = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getCategory() == category) {
                taskArrayList.add(task);
            }
        }
        return taskArrayList;
    }

    @Override
    public void onItemClicked(View view, int position) {
        Task task = myAdapter.getItemByPosition(position);
        int id = view.getId();
        if (id == R.id.delete_task_btn) {
            showDeletingAlertDialog(task.getTaskId());
        } else if (id == R.id.tv_task_details) {
            if (task.getRepetition() == TasksContract.TaskEntity.DAILY_TASK) {
                Intent intent = new Intent(this, ProgressActivity.class);
                intent.putExtra("task_name", task.getTaskName());
                startActivity(intent);
            } else
                showDetailsDialog(task);
        } else if (id == R.id.task_status_chb) {
            task.setStatus(task.getStatus() == TasksContract.TaskEntity.TASK_DONE ?
                    TasksContract.TaskEntity.TASK_UNDONE : TasksContract.TaskEntity.TASK_DONE);
            testViewModel.updateTask(task);
        } else if (id == R.id.edit_tasks_btn) {
            Intent updateIntent = new Intent(MainActivity.this, AddTaskActivity.class);
            updateIntent.putExtra(KEY_UPDATE_TASK_ID, task.getTaskId());
            startActivity(updateIntent);
        }
    }

    @Override
    public void handleDeleteTaskAlertDialog(int taskId) {
        testViewModel.deleteTaskById(taskId);
    }

    @Override
    public void handleDeleteAllDataAlertDialog() {
        testViewModel.deleteAllDataInDB();
    }

    private void setThemeMode(MenuItem menuItem) {
        // Get the night mode state of the app.
        int nightMode = AppCompatDelegate.getDefaultNightMode();
        //Set the theme mode for the restarted activity
        if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            menuItem.setTitle(R.string.day_mode);
        } else {
            AppCompatDelegate.setDefaultNightMode
                    (AppCompatDelegate.MODE_NIGHT_YES);
            menuItem.setTitle(R.string.night_mode);
        }
// Recreate the activity for the theme change to take effect.
//        recreate();
    }

    private void setTextInSwitchModeMenu(MenuItem menuItem) {
        //Set the theme mode for the restarted activity
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            menuItem.setTitle(R.string.day_mode);
        else
            menuItem.setTitle(R.string.night_mode);

    }
}

