package com.example.nadav.finalproject;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ToDoListCursorAdapter extends CursorAdapter {
    LayoutInflater inflater;

    public ToDoListCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, true);
        inflater = LayoutInflater.from(context);

    }

    public void bindView(View view, final Context context, final Cursor cursor) {

        final TextView task = (TextView) view.findViewById(R.id.task);
        CheckBox checkBox = view.findViewById(R.id.taskCheckBox);
        Button del = view.findViewById(R.id.delete);
        LinearLayout line = (LinearLayout) view.findViewById(R.id.line1);
        task.setText(cursor.getString(cursor.getColumnIndex(Constants.ToDoList.TASK)));

        final int id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(Constants.InvitedTable._ID)));

        checkBox.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {

                                          if ((task.getPaintFlags() & Paint.STRIKE_THRU_TEXT_FLAG) > 0)
                                          {
                                              task.setPaintFlags( task.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                                              ((TodoList)context).CheckBoxClick(id, v);
                                          }
                                          else
                                          {
                                              task.setPaintFlags(task.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                              ((TodoList)context).CheckBoxClick(id, v);
                                          }
                                      }
                                  });

        del.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ((TodoList)context).removeTaskBtn(id, v);
                                }
                            });


//        task.setText(cursor.getString(cursor.getColumnIndex(Constants.ToDoList.TASK)));

    }


    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        return inflater.inflate(R.layout.to_do_list, parent, false);
    }

}
