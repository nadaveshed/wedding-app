package com.example.nadav.finalproject;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyCursorAdapter extends CursorAdapter {
    LayoutInflater inflater;

    public MyCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, true);
        inflater = LayoutInflater.from(context);
    }

    public void bindView(View view, final Context context, Cursor cursor) {

        TextView name = (TextView) view.findViewById(R.id.name);
        TextView phonenum = (TextView) view.findViewById(R.id.phone);
        TextView invites = (TextView) view.findViewById(R.id.numberOfInvited);
        TextView coming = (TextView) view.findViewById(R.id.isComing);
        LinearLayout line = (LinearLayout) view.findViewById(R.id.line1);
        Button plus = view.findViewById(R.id.btnSubmitplus);
        Button minus = view.findViewById(R.id.btnSubmitminus);
        CheckBox checkBox = view.findViewById(R.id.sendCheckBox);

        final int id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(Constants.InvitedTable._ID)));

        plus.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          ((MainActivity) context).clickSubmitplus(id, v);
                                      }
                                  });

        minus.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          ((MainActivity) context).clickSubmitminus(id, v);
                                      }
                                  });

        checkBox.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          ((MainActivity) context).CheckBoxClick(id, v);
                                      }
                                  });

        name.setText(cursor.getString(cursor.getColumnIndex(Constants.InvitedTable.NAME)));
        phonenum.setText("0" + cursor.getString(cursor.getColumnIndex(Constants.InvitedTable.PHONE)));
        invites.setText(cursor.getString(cursor.getColumnIndex(Constants.InvitedTable.INVITED)));
        coming.setText(cursor.getString(cursor.getColumnIndex(Constants.InvitedTable.COMING)));

    }

    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        return inflater.inflate(R.layout.items_view, parent, false);
    }

}
