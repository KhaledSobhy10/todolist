package com.example.todolist.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.todolist.R;
import com.example.todolist.Utilities.DateUtility;
import com.example.todolist.data.AppRepository;
import com.example.todolist.data.Task;
import com.example.todolist.data.TasksContract;
import com.example.todolist.databinding.ActivityAddTaskBinding;

import java.util.Objects;

import static com.example.todolist.Utilities.DateUtility.getCurrentDate;

public class AddTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static boolean isUpdating = false;
    ActivityAddTaskBinding activityAddTaskBinding;
    private static final String TAG = AddTaskActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddTaskBinding = ActivityAddTaskBinding.inflate(getLayoutInflater());
        View view = activityAddTaskBinding.getRoot();
        setContentView(view);
        isUpdating = getIntent().hasExtra(MainActivity.KEY_UPDATE_TASK_ID);

        if (isUpdating) {
            setupUpdateActivity();
        } else {
            setupAddActivity();
        }

        activityAddTaskBinding.dateTextInputLayout.setStartIconOnClickListener(v -> {
            DialogPickers datePickerDialog = new DialogPickers();
            Bundle bundle = new Bundle();
            bundle.putString(DialogPickers.PICKER_TYPE, DialogPickers.DATE_PICKER);
            datePickerDialog.setArguments(bundle);
            datePickerDialog.show(getSupportFragmentManager(), DialogPickers.DATE_PICKER);
        });

        activityAddTaskBinding.addTaskNowBtn.setOnClickListener((btn) -> {
            Task task = getTaskFromViews();
            if (task != null) {
                if (isUpdating) {
                    task.setTaskId(getIntent().getIntExtra(MainActivity.KEY_UPDATE_TASK_ID, -1));
                    AppRepository.getInstance(getApplication()).updateTaskInDB(task);
                } else {
                    AppRepository.getInstance(getApplication()).insertTask(task);
                }
                finish();

            }


        });

    }

    // task input from user
    private Task getTaskFromViews() {
        String date = Objects.requireNonNull(activityAddTaskBinding.dateEt.getText()).toString();
        String taskName = Objects.requireNonNull(activityAddTaskBinding.topicEt.getText()).toString();
        if (taskName.isEmpty()) {
            activityAddTaskBinding.topicEt.setError("topic can't be empty");
            return null;
        }
        if (!DateUtility.isValidData(date)) {
            activityAddTaskBinding.dateEt.setError("invalid date must be " + DateUtility.datePattern + " and future date ");
            return null;
        }
        String description = Objects.requireNonNull(activityAddTaskBinding.descriptionEt.getText()).toString();
        int taskStatus = TasksContract.TaskEntity.TASK_UNDONE;
        int category;
        int repetition;
        int checkedChipId = activityAddTaskBinding.categoryChg.getCheckedChipId();
        if (checkedChipId == R.id.learning_ch) {
            category = TasksContract.TaskEntity.TASK_CATEGORY_STUDY;
        } else if (checkedChipId == R.id.working_ch) {
            category = TasksContract.TaskEntity.TASK_CATEGORY_WORKING;
        } else if (checkedChipId == R.id.training_ch) {
            category = TasksContract.TaskEntity.TASK_CATEGORY_TRAINING;
        } else if (checkedChipId == R.id.health_ch) {
            category = TasksContract.TaskEntity.TASK_CATEGORY_HEALTH;
        } else {
            category = TasksContract.TaskEntity.TASK_CATEGORY_FAMILY;
        }
        int chipId = activityAddTaskBinding.repetitionChipGroup.getCheckedChipId();
        if (chipId == R.id.daily_rep_ch) {
            repetition = TasksContract.TaskEntity.DAILY_TASK;
        } else if (chipId == R.id.weekly_rep_ch) {
            repetition = TasksContract.TaskEntity.WEEKLY_TASK;
        } else {
            repetition = TasksContract.TaskEntity.TASK_NONE_REPETITION;
        }

        int priorityChipId = activityAddTaskBinding.priorityChipGroup.getCheckedChipId();
        int priority = TasksContract.TaskEntity.TASK_PRIORITY_LOW;
        if (priorityChipId == R.id.high_priority_chip) {
            priority = TasksContract.TaskEntity.TASK_PRIORITY_HIGH;
        } else if (priorityChipId == R.id.medium_priority_chip) {
            priority = TasksContract.TaskEntity.TASK_PRIORITY_MEDIUM;
        }




        return new Task(taskName, taskStatus, repetition, category, date, description, priority);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = DateUtility.IntegerDateToString(year, month, dayOfMonth);
        activityAddTaskBinding.dateEt.setText(date);
    }

    // inti views if update task
    public void setupUpdateActivity() {
        activityAddTaskBinding.createTaskLabel.setText(R.string.update_task_label);
        activityAddTaskBinding.addTaskNowBtn.setText(R.string.update);
        LiveData<Task> taskLiveData = AppRepository.getInstance(getApplication()).getTaskByIdFromDB(getIntent().getIntExtra(MainActivity.KEY_UPDATE_TASK_ID, -1));
        taskLiveData.observe(this, (task) -> {
            activityAddTaskBinding.topicEt.setText(task.getTaskName());
            activityAddTaskBinding.descriptionEt.setText(task.getDescription());
            activityAddTaskBinding.dateEt.setText(task.getDate());
            int category = task.getCategory();
            int repetition = task.getRepetition();
            int priority = task.getPriority();

            switch (category) {
                case TasksContract.TaskEntity.TASK_CATEGORY_STUDY:
                    activityAddTaskBinding.learningCh.setChecked(true);
                    break;
                case TasksContract.TaskEntity.TASK_CATEGORY_WORKING:
                    activityAddTaskBinding.workingCh.setChecked(true);
                    break;
                case TasksContract.TaskEntity.TASK_CATEGORY_TRAINING:
                    activityAddTaskBinding.trainingCh.setChecked(true);
                    break;
                case TasksContract.TaskEntity.TASK_CATEGORY_HEALTH:
                    activityAddTaskBinding.healthCh.setChecked(true);
                    break;
                default:
                    activityAddTaskBinding.familyCh.setChecked(true);
            }

            switch (repetition) {
                case TasksContract.TaskEntity.DAILY_TASK:
                    activityAddTaskBinding.dailyRepCh.setChecked(true);
                    break;
                case TasksContract.TaskEntity.WEEKLY_TASK:
                    activityAddTaskBinding.weeklyRepCh.setChecked(true);
                    break;
                default:
                    activityAddTaskBinding.onceRepCh.setChecked(true);
            }

            switch (priority) {
                case TasksContract.TaskEntity.TASK_PRIORITY_HIGH:
                    activityAddTaskBinding.highPriorityChip.setChecked(true);
                    break;
                case TasksContract.TaskEntity.TASK_PRIORITY_MEDIUM:
                    activityAddTaskBinding.mediumPriorityChip.setChecked(true);
                    break;
                case TasksContract.TaskEntity.TASK_PRIORITY_LOW:
                    activityAddTaskBinding.lowPriorityChip.setChecked(true);
                    break;
            }


        });


    }

    // inti view if add task
    public void setupAddActivity() {
        activityAddTaskBinding.dateEt.setText(getCurrentDate());
    }
}