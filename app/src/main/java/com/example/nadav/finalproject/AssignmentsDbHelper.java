package com.example.nadav.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.nadav.finalproject.Constants.InvitedTable;
import com.example.nadav.finalproject.Constants.ToDoList;

public class AssignmentsDbHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Constants.InvitedTable.TABLE_NAME + " (" +
                    InvitedTable._ID +           " INTEGER PRIMARY KEY," +
                    InvitedTable.NAME +     " STRING," +
                    InvitedTable.PHONE +    " INTEGER," +
                    InvitedTable.INVITED +     " INTEGER, " +
                    InvitedTable.COMING +     " STRING" +

                    ");";


    private static final String SQL_CREATE_TODOLIST =
            "CREATE TABLE " + Constants.ToDoList.TABLE_NAME + " (" +
                    ToDoList._ID +           " INTEGER PRIMARY KEY," +
                    ToDoList.TASK +     " STRING" +
                    ");";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + InvitedTable.TABLE_NAME;

    private static final String SQL_DELETE_TODOLIST =
            "DROP TABLE IF EXISTS " + ToDoList.TABLE_NAME;


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Invited.db";


    public AssignmentsDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_TODOLIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // implement migration policy if have
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_TODOLIST);
        onCreate(db);
    }


    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onUpgrade(db, oldVersion, newVersion);
    }
}
