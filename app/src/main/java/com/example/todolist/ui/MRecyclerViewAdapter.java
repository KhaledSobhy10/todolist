package com.example.todolist.ui;


import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.data.Task;
import com.example.todolist.data.TasksContract;
import com.example.todolist.databinding.CardItemLayoutBinding;

import java.util.ArrayList;

public class MRecyclerViewAdapter extends RecyclerView.Adapter<MRecyclerViewAdapter.MViewHolder> {
    private static ItemClickListener listener;
    private ArrayList<Task> taskArrayList;


    public MRecyclerViewAdapter(ItemClickListener itemClickListener) {
        listener = itemClickListener;
    }



    @NonNull
    @Override
    public MViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardItemLayoutBinding binding = CardItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull MViewHolder holder, int position) {

        Task task = taskArrayList.get(position);

        holder.binding.tvTask.setText(task.getTaskName());
        holder.binding.taskStatusChb.setChecked(task.getStatus() == 1);
        if (task.getStatus() == TasksContract.TaskEntity.TASK_DONE)
        holder.binding.tvTask.setPaintFlags(holder.binding.tvTask.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        else holder.binding.tvTask.setPaintFlags(Paint.ANTI_ALIAS_FLAG);

    }


    @Override
    public int getItemCount() {
        if (taskArrayList != null)
            return taskArrayList.size();
        else return 0;
    }


    public void setData(ArrayList<Task> newData) {
        taskArrayList = newData;
        notifyDataSetChanged();
    }

    public Task getItemByPosition(int adapterPosition) {
        if (taskArrayList != null)
            return taskArrayList.get(adapterPosition);
        else return null;
    }

    public interface ItemClickListener {
        void onItemClicked(View view, int position);
    }

    public static class MViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardItemLayoutBinding binding;

        public MViewHolder(@NonNull CardItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.taskStatusChb.setOnClickListener(this);
            binding.tvTaskDetails.setOnClickListener(this);
            binding.deleteTaskBtn.setOnClickListener(this);
            binding.editTasksBtn.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            listener.onItemClicked(v, getAdapterPosition());
        }


    }


}
