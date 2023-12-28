package com.example.sqlite;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class ToDoDAO {
    private final DbHelper dbHelper;

    public ToDoDAO(Context context) {
        this.dbHelper = new DbHelper(context);
    }

    public ArrayList<Todo> getListTodo() {

        ArrayList<Todo> list = new ArrayList<>();

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        database.beginTransaction();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM TODO", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    list.add(new Todo(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getInt(5)));
                }
                while (cursor.moveToNext());
                database.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.e(TAG, "getListTodo: " + e);
        } finally {
            database.endTransaction();
        }
        return list;
    }
    public boolean addTodo(Todo toDo){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.beginTransaction();
        ContentValues values = new ContentValues();
        values.put("TITLE",toDo.getTitle());
        values.put("CONTENT",toDo.getContent());
        values.put("DATE",toDo.getDate());
        values.put("TYPE",toDo.getType());
        values.put("STATUS",toDo.getStatus());
        long check = database.insert("TODO" , null, values);
        return check != -1;
    }

    public boolean updateTodo(Todo toDo) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("TITLE", toDo.getTitle());
        values.put("CONTENT", toDo.getContent());
        values.put("DATE", toDo.getDate());
        values.put("TYPE", toDo.getType());
        values.put("STATUS", toDo.getStatus());

        int rowsAffected = database.update("TODO", values, "ID=?", new String[]{String.valueOf(toDo.getId())});

        return rowsAffected > 0;
    }

    public boolean deleteTodo(long todoId) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        int rowsAffected = database.delete("TODO", "ID=?", new String[]{String.valueOf(todoId)});
        return rowsAffected > 0;
    }
}
