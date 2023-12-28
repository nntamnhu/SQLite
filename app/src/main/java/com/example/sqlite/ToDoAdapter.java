package com.example.sqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ToDoAdapter extends ArrayAdapter<Todo> {
    private Context context;
    private List<Todo> tasks;

    public ToDoAdapter(Context context, List<Todo> tasks) {
        super(context, 0, tasks);
        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Todo task = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todo, parent, false);
        }
        TextView taskTitle = convertView.findViewById(R.id.task_title);
        TextView taskContent = convertView.findViewById(R.id.task_content);
        TextView taskDate = convertView.findViewById(R.id.task_date);
        TextView taskType = convertView.findViewById(R.id.task_type);

        taskTitle.setText(task.getTitle());
        taskContent.setText(task.getContent());
        taskDate.setText(task.getDate());
        taskType.setText(task.getType());
        return convertView;
    }



}
