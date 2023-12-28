package com.example.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context){
        super(context,"TodoDatabase",null, 1 );
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE TODO(ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "TITLE TEXT, CONTEXT TEXT, DATE TEXT, TYPE TEXT, STATUS INREGER)";
        sqLiteDatabase.execSQL(sql);

        String data = "INSERT INTO TODO VALUES(1, 'Hoc Java co ban', '27/02/2023', 'Binh Thuong', 1),"+
                "(2, 'Hoc React Native co ban', '24/03/2023', 'Kho', 0)," +
                "(3, 'Hoc Kotlin co ban', '27/02/2023', 'Binh Thuong', 0)";

        sqLiteDatabase.execSQL(data);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i != i1)
        {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS TODO");

            onCreate(sqLiteDatabase);
        }
    }
}
