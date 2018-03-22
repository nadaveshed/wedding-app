package com.example.nadav.finalproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nadav.finalproject.Constants.ToDoList;


public class AddTask extends AppCompatActivity {

    AssignmentsDbHelper dbHelper;
    SQLiteDatabase db;

    EditText addTask;

    ListView to_do_list_item;

    String inputText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        addTask = (EditText)findViewById(R.id.addTask);
        to_do_list_item = (ListView)findViewById(R.id.to_do_list_item);


        dbHelper = new AssignmentsDbHelper(this);
        db = dbHelper.getWritableDatabase();

        String[] cols2 = {ToDoList._ID, ToDoList.TASK};

        Cursor c = db.query(
                ToDoList.TABLE_NAME,
                cols2,
                null,
                null,
                null,
                null,
                Constants.ToDoList.TASK + " ASC"
        );

        ToDoListCursorAdapter mca = new ToDoListCursorAdapter(this, c);

    }

    public void addTaskBtn(View view) {

        inputText = addTask.getText().toString();

        LinearLayout layout = (LinearLayout ) view.getParent();
        TextView task = (TextView)layout.findViewById(R.id.task);

//        Log.e("AddTask", "addTaskBtn  " + task.getText());
        if(!inputText.matches(""))
        {
            ContentValues values = new ContentValues();
            values.put(ToDoList.TASK, inputText);

            long id;
            id = db.insert(ToDoList.TABLE_NAME,null,values);
            db.close();
            db = dbHelper.getReadableDatabase();
            //Log.e("AddInvited", "asasasa "+addTask.getText().toString());
            Toast.makeText(AddTask.this,
                    "המשימה נוספה בהצלחה :-)", Toast.LENGTH_LONG).show();
            addTask.setText("");

        }
        else
        {
            Toast.makeText(AddTask.this,
                    "אופס! לא רשמת משימה חדשה", Toast.LENGTH_LONG).show();
        }

    }

    public void backBtn(View view) {
        Intent myIntent = new Intent(getApplicationContext(), TodoList.class);
        startActivityForResult(myIntent, 0);
    }
}
