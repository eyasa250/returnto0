package com.example.returnto0.ui.task;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.returnto0.R;
import com.example.returnto0.room.Room;
import com.example.returnto0.task.Task;

import java.util.List;
public class TaskSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private List<Task> taskList;

    public TaskSpinnerAdapter(List<Task> taskList) {
        this.taskList = taskList;
    }
    public void updateList(List<Task> newList) {
        taskList = newList;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int position) {
        return taskList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_item_task, parent, false);
        }

        TextView taskNameTextView = convertView.findViewById(R.id.task_name_text_view);
        Task task = taskList.get(position);
        taskNameTextView.setText(task.getName()); // Assuming your Task class has a getName() method

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}
