    package com.example.todolist.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.todolist.R;
import com.example.todolist.data.Task;
import com.example.todolist.databinding.TaskDetailsDialogBinding;

import java.util.Objects;

public class MyDialogFragment extends DialogFragment {
    public static final String KEY_DIALOG_TYPE = "dialog_type";
    public static final String DELETE_TASK_ALERT = "delete_alert";
    public static final String DELETE_ALL_DATA_ALERT = "delete_all_data_alert";
    public static final String DETAILS_TASK_DIALOG = "details_dialog";

    public static final String KEY_TASK_ID = "task_id";
    private AddingTaskListener addingTaskListener;

    public MyDialogFragment() {

    }

    // Override the Fragment.onAttach() method to instantiate the AddingTaskListener
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            addingTaskListener = (AddingTaskListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity()
                    + " must implement DialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog mDialog = super.onCreateDialog(savedInstanceState);
        if (getArguments() != null) {
            String dialogType = getArguments().getString(KEY_DIALOG_TYPE);
            switch (Objects.requireNonNull(dialogType)) {
                case DELETE_TASK_ALERT:
                    int taskId = getArguments().getInt(KEY_TASK_ID);
                    AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                    builder.setMessage(R.string.delete_task_warning);
                    builder.setPositiveButton(R.string.ok, (dialog, which) -> addingTaskListener.handleDeleteTaskAlertDialog(taskId));
                    builder.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss());
                    mDialog = builder.create();
                    break;
                case DELETE_ALL_DATA_ALERT:
                    AlertDialog.Builder builderDeleteAllData = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                    builderDeleteAllData.setMessage(R.string.delete_all_data_warning);
                    builderDeleteAllData.setPositiveButton(R.string.ok, (dialog, which) -> {
                        addingTaskListener.handleDeleteAllDataAlertDialog();
                        dialog.dismiss();
                    });
                    builderDeleteAllData.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss());
                    mDialog = builderDeleteAllData.create();
                    break;

            }
        }
        return mDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return getLayoutInflater().inflate(R.layout.task_details_dialog, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        String dialogType = getArguments() != null ? getArguments().getString(KEY_DIALOG_TYPE) : null;
        if (dialogType != null) {
            if (MyDialogFragment.DETAILS_TASK_DIALOG.equals(dialogType)) {
                TaskDetailsDialogBinding binding2 = TaskDetailsDialogBinding.bind(view);
                Task task = getArguments().getParcelable("task");
                if (task != null) {
                    binding2.detailsTaskNameTv.setText(task.getTaskName());
                    binding2.detailRepetitionTv.setText(task.getStringRepetition());
                    if (task.getDescription() != null && !task.getDescription().isEmpty())
                        binding2.detailsTaskDescriptionTv.setText(task.getDescription());
                    GradientDrawable background = (GradientDrawable) binding2.priorityShapeIv.getBackground();
                   background.setColor(task.getColorRGBForPriority(getResources()));

                }
            }
        }

    }


    public interface AddingTaskListener {
        void handleDeleteTaskAlertDialog(int taskId);

        void handleDeleteAllDataAlertDialog();
    }
}
