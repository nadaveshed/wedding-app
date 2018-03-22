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

import com.example.nadav.finalproject.Constants.InvitedTable;

public class AddInvited extends AppCompatActivity {

    AssignmentsDbHelper dbHelper;
    SQLiteDatabase db;
    MyCursorAdapter mca;

    EditText invitedName;
    EditText invitedPhone;
    EditText numOfInvited;

    ListView list_item;

    String inputName;
    String inputPhone;
    String inputCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_invited);

        invitedName = (EditText)findViewById(R.id.editText_invitedName);
        invitedPhone = (EditText)findViewById(R.id.editText_invitedPhone);
        numOfInvited = (EditText)findViewById(R.id.editText_numOfInvited);
        list_item = (ListView)findViewById(R.id.list_item);

        dbHelper = new AssignmentsDbHelper(this);
        db = dbHelper.getWritableDatabase();

        String[] cols2 = {InvitedTable._ID, InvitedTable.NAME, InvitedTable.PHONE, InvitedTable.INVITED, InvitedTable.COMING };

        Cursor c = db.query(
                InvitedTable.TABLE_NAME,
                cols2,
                null,
                null,
                null,
                null,
                InvitedTable.NAME + " ASC"
        );

        MyCursorAdapter mca = new MyCursorAdapter(this, c);
       // list_item.setAdapter(mca);
    }

    public void onClickback(View view)
    {
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
    }

    public void onClickAdd(View view)
    {
        inputName = invitedName.getText().toString();
        inputPhone = invitedPhone.getText().toString();
        inputCount = numOfInvited.getText().toString();

        LinearLayout layout = (LinearLayout ) view.getParent();
        TextView isComing = (TextView)layout.findViewById(R.id.isComing);

        if(!inputName.matches("") && !inputCount.matches("") && !inputPhone.matches(""))
        {
            if(inputPhone.length() != 10  ){
                Toast.makeText(this,"אנא הכנס/י"+"\n"+"מספר טלפון תקין", Toast.LENGTH_SHORT).show();
                // am_checked=0;
                //Log.e("AddInvited",inputPhone);
            }
            else
            {
                ContentValues values = new ContentValues();
                values.put(InvitedTable.NAME,inputName);
                values.put(InvitedTable.PHONE,inputPhone);
                values.put(InvitedTable.INVITED,inputCount);
                values.put(InvitedTable.COMING,"0");

                long id;
                id = db.insert(InvitedTable.TABLE_NAME,null,values);
                db.close();
                db = dbHelper.getReadableDatabase();
                //Log.e("AddInvited", "11111 "+invitedName.getText().toString());

                Toast.makeText(AddInvited.this,
                        "המוזמן נוסף בהצלחה :-)", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(AddInvited.this,
                    "אנא מלא/י את הפרטים", Toast.LENGTH_LONG).show();
            Log.e("AddInvited", "NOOOOOOOOOO!");
        }
    }
}
