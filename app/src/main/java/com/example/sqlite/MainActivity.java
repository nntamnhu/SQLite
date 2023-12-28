package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView todoListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoListView = findViewById(R.id.todoListView);
        ToDoDAO toDoDAO = new ToDoDAO(this);
        ArrayList<Todo> list = toDoDAO.getListTodo();
        ToDoAdapter toDoAdapter = new ToDoAdapter(this, list);
        todoListView.setAdapter(toDoAdapter);
    }
}