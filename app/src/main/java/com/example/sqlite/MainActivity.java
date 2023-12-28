package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private int selectedTodoId = -1;
    private ToDoDAO toDoDAO;
    ListView todoListView;
    EditText edtTitle, edtContent, edtDate, edtType;
    Button btnAdd, btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);
        edtDate = findViewById(R.id.edtDate);
        edtType = findViewById(R.id.edtType);
        btnAdd = findViewById(R.id.btnAdd);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
        todoListView = findViewById(R.id.todoListView);

        toDoDAO = new ToDoDAO(this);

        ArrayList<Todo> list = toDoDAO.getListTodo();
        ToDoAdapter toDoAdapter = new ToDoAdapter(this, list);
        todoListView.setAdapter(toDoAdapter);

        Button add = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToDo();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateToDo();
            }
        });

        // Xử lý sự kiện cho nút Delete
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteToDo();
            }
        });
        todoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Todo selectedToDo = (Todo) parent.getItemAtPosition(position);
                displaySelectedToDo(selectedToDo);
            }
        });
    }


        private void addToDo() {
            Todo newToDo = new Todo(0,
                    edtTitle.getText().toString(),
                    edtContent.getText().toString(),
                    edtDate.getText().toString(),
                    edtType.getText().toString(),
                    0);
            boolean isSuccess = toDoDAO.addTodo(newToDo);

            if (isSuccess) {
                Toast.makeText(MainActivity.this, "Thêm công việc thành công", Toast.LENGTH_SHORT).show();
                refreshToDoList();
            } else {
                Toast.makeText(MainActivity.this, "Thêm công việc thất bại", Toast.LENGTH_SHORT).show();
            }
    }

    private void refreshToDoList() {
        ArrayList<Todo> toDoList = toDoDAO.getListTodo();
        ToDoAdapter toDoAdapter = new ToDoAdapter(this, toDoList);
        todoListView.setAdapter(toDoAdapter);
    }

    private void displaySelectedToDo(Todo selectedToDo) {
        edtTitle.setText(selectedToDo.getTitle());
        edtContent.setText(selectedToDo.getContent());
        edtDate.setText(selectedToDo.getDate());
        edtType.setText(selectedToDo.getType());

        // Lưu ID vào biến selectedTodoId để sử dụng sau này
        selectedTodoId = selectedToDo.getId();
    }

    private void updateToDo() {
        String title = edtTitle.getText().toString();
        String content = edtContent.getText().toString();
        String date = edtDate.getText().toString();
        String type = edtType.getText().toString();

        Todo updatedToDo = new Todo(selectedTodoId, title, content, date, type, 0);

        boolean isSuccess = toDoDAO.updateTodo(updatedToDo);

        if (isSuccess) {
            Toast.makeText(MainActivity.this, "Cập nhật công việc thành công", Toast.LENGTH_SHORT).show();
            refreshToDoList();
        } else {
            Toast.makeText(MainActivity.this, "Cập nhật công việc thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteToDo() {
        if (selectedTodoId != -1) {
            boolean isSuccess = toDoDAO.deleteTodo(selectedTodoId);

            if (isSuccess) {
                Toast.makeText(MainActivity.this, "Xóa công việc thành công", Toast.LENGTH_SHORT).show();
                refreshToDoList();
            } else {
                Toast.makeText(MainActivity.this, "Xóa công việc thất bại", Toast.LENGTH_SHORT).show();
            }

            // Sau khi xóa, đặt lại giá trị của selectedTodoId
            selectedTodoId = -1;
        } else {
            Toast.makeText(MainActivity.this, "Vui lòng chọn công việc để xóa", Toast.LENGTH_SHORT).show();
        }
    }

}