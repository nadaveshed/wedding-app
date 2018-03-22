package com.example.nadav.finalproject;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nadav.finalproject.Constants.ToDoList;

public class TodoList extends AppCompatActivity {

    AssignmentsDbHelper dbHelper;
    SQLiteDatabase db;
    ToDoListCursorAdapter mca;

    ListView to_do_list_item;

    Cursor c;

    String inputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        to_do_list_item = (ListView) findViewById(R.id.to_do_list_item);
    }

    protected void onStart()
    {
        super.onStart();

        dbHelper = new AssignmentsDbHelper(this);
        db = dbHelper.getWritableDatabase();

        String[] cols2 = {ToDoList._ID, ToDoList.TASK};

        c = db.query(
                ToDoList.TABLE_NAME,
                cols2,
                null,
                null,
                null,
                null,
                null
        );

        mca = new ToDoListCursorAdapter(this, c);
        to_do_list_item.setAdapter(mca);
        //Log.e("TodoList", "removeTaskBtn");
    }

    public void removeTaskBtn(int id, View v) {
        //Log.e("TodoList", "removeTaskBtn");
        db.delete(Constants.ToDoList.TABLE_NAME, Constants.ToDoList._ID + " = ?", new String[]
                {
                        String.valueOf(id)
                });
        c.requery();
        mca.notifyDataSetChanged();
    }

    public void addTaskBtn(View view) {
        //Log.e("TodoList", "addTaskBtn");

        Intent i = new Intent(this,AddTask.class);
        startActivity(i);
    }

    public void invitedList(View view) {

        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
    }

    public void CheckBoxClick(int id, View v) {
    }
}
