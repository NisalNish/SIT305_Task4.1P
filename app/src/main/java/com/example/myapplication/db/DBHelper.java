package com.example.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.model.Task;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context) {
        super(context, "task.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE tbl_task (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT, date TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tbl_task");
        onCreate(sqLiteDatabase);
    }

    public long addTask(Task t) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("title", t.getTitle());
        cv.put("description", t.getDescription());
        cv.put("date",t.getDate());

        return db.insert("tbl_task",null,cv);
    }

    public ArrayList<Task> getAllTasks(){

        ArrayList<Task> tasks = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * from tbl_task", null);

        if(cursor.moveToFirst())
        {
            do {

                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String description = cursor.getString(2);
                String date = cursor.getString(3);

                Task t = new Task( id, title, description, date);
                tasks.add(t);

            }while (cursor.moveToNext());
        }

        return tasks;
    }

    public int updateTask(Task t) {

        SQLiteDatabase db  = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("title", t.getTitle());
        cv.put("description", t.getDescription());
        cv.put("date",t.getDate());

        return db.update("tbl_task", cv, "id=?", new String[]{String.valueOf(t.getId())});


    }

    public int deleteTask(int id) {

        SQLiteDatabase db = getWritableDatabase();

        return db.delete("tbl_task", "id=?", new String[]{String.valueOf(id)});
    }
}
