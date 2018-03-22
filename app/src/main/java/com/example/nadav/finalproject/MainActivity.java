package com.example.nadav.finalproject;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nadav.finalproject.Constants.InvitedTable;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    AssignmentsDbHelper dbHelper;
    SQLiteDatabase db;
    MyCursorAdapter mca;

    TextView Name;
    TextView Phone;
    TextView Invited;
    TextView isComing;

    ListView list_item;

    Cursor c2;

    int number;
    long id;

    String messege = "שלום \n הנך מוזמן לחתונה של עינב ואדם ";
    ArrayList<String> selected = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.SEND_SMS}, 1);
        if(checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
            requestPermissions(new String[]{Manifest.permission.SEND_SMS,Manifest.permission.READ_CONTACTS,Manifest.permission.READ_PHONE_STATE},1);
        setContentView(R.layout.activity_main);
        list_item = (ListView) findViewById(R.id.list_item);

        Name = (TextView) findViewById(R.id.Name);
        Phone = (TextView) findViewById(R.id.Phone);
        Invited = (TextView) findViewById(R.id.NumberOfInvited);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.e("MainActivity", "premission request");
        switch (requestCode) {
            case 1: {
                //If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.w("MainActivity", "Permissions was granteed");
                    onStart();

                } else {
                    Log.e("MainActivity", "Permissions was denied");
                    onStop();
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        dbHelper = new AssignmentsDbHelper(this);
        db = dbHelper.getWritableDatabase();

        String[] cols2 = {InvitedTable._ID, InvitedTable.NAME, InvitedTable.PHONE, InvitedTable.INVITED, InvitedTable.COMING};

        c2 = db.query(
                InvitedTable.TABLE_NAME,
                cols2,
                null,
                null,
                null,
                null,
                InvitedTable.NAME + " ASC"
        );

        mca = new MyCursorAdapter(this, c2);
        list_item.setAdapter(mca);

    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    public void sendSMSbtn(View view)
    {
        //Log.e("MainActivity", "Pressed btn 1");
        //Log.e("MainActivity", "SMS " + number);
        SmsManager sms = SmsManager.getDefault();
        ArrayList<String> parts = sms.divideMessage(messege);
        if(!selected.isEmpty()) {
            for (String select : selected) {
                sms.sendMultipartTextMessage("0" + select, null, parts, null, null);
                //Log.e("MainActivity", "555555    " + selected);
            }

            Toast.makeText(MainActivity.this,
                    "ההודעה נשלחה! (^o^)", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(MainActivity.this,
                    "לא נבחר אף מוזמן!", Toast.LENGTH_LONG).show();
        }
    }

    public void addUserBtn(View view)
    {
        //Log.e("MainActivity", "Pressed btn 2");
        Intent i = new Intent(this,AddInvited.class);
        startActivity(i);
    }

    public void toDoListBtn(View view)
    {
        //Log.e("MainActivity", "Pressed btn 3");
        Intent i = new Intent(this,TodoList.class);
        startActivity(i);
    }

    private void createTable()
    {
        String[] cols2 = {InvitedTable._ID, InvitedTable.NAME, InvitedTable.PHONE, InvitedTable.INVITED, InvitedTable.COMING};

        c2 = db.query(
                InvitedTable.TABLE_NAME,
                cols2,
                null,
                null,
                null,
                null,
                InvitedTable.NAME + " ASC"
        );

        mca = new MyCursorAdapter(this, c2);
        list_item.setAdapter(mca);
    }

    public void clickSubmitplus(int lineID, View view)
    {
        LinearLayout layout = (LinearLayout ) view.getParent();
        isComing = (TextView)layout.findViewById(R.id.isComing);

        //Log.d("Current Quantity ",isComing.getText().toString());

        number = Integer.parseInt(isComing.getText().toString());
        number++;
        isComing.setText(String.valueOf(number));

        //Log.e("MainActivity", "Pressed btn +");

        ContentValues values = new ContentValues();
        values.put(InvitedTable.COMING, String.valueOf(isComing.getText()));

        id = db.update(InvitedTable.TABLE_NAME, values,Constants.InvitedTable._ID + " = ?", new String[]
                {
                        String.valueOf(lineID)
                });
        c2.requery();
        mca.notifyDataSetChanged();
        db.close();
        db = dbHelper.getReadableDatabase();

        createTable();
    }

    public void clickSubmitminus(int lineID, View view)
    {
        LinearLayout layout = (LinearLayout ) view.getParent();
        isComing = (TextView)layout.findViewById(R.id.isComing);

        //Log.d("Current Quantity ",isComing.getText().toString());

        number = Integer.parseInt(isComing.getText().toString());
        if(number <= 0)
            isComing.setText("0");
        else
        {
            number--;
            isComing.setText(String.valueOf(number));
        }
        isComing.setText(String.valueOf(number));

        //Log.e("MainActivity", "Pressed btn +");

        ContentValues values = new ContentValues();
        values.put(InvitedTable.COMING, String.valueOf(isComing.getText()));

        id = db.update(InvitedTable.TABLE_NAME, values,Constants.InvitedTable._ID + " = ?", new String[]
                {
                        String.valueOf(lineID)
                });
        c2.requery();
        mca.notifyDataSetChanged();

        db.close();
        db = dbHelper.getReadableDatabase();

        createTable();

    }

    public void CheckBoxClick(int lineID, View view)
    {
        if (((CheckBox) view).isChecked()) {
//            Toast.makeText(MainActivity.this,
//                    "Bro, try Android :)", Toast.LENGTH_LONG).show();

            String[] cols2 = {InvitedTable._ID, InvitedTable.NAME, InvitedTable.PHONE, InvitedTable.INVITED, InvitedTable.COMING};

            c2 = db.query(
                    InvitedTable.TABLE_NAME,
                    cols2,
                    InvitedTable._ID + " = ?",
                    new String[]{String.valueOf(lineID)},
                    null,
                    null,
                    null
            );

            c2.moveToFirst();
            selected.add(String.valueOf(Integer.parseInt(c2.getString(2))));
            //Log.e("MainActivity", "6666    "+ selected);
        }
        else
        {
            String[] cols2 = {InvitedTable._ID, InvitedTable.NAME, InvitedTable.PHONE, InvitedTable.INVITED, InvitedTable.COMING};

            c2 = db.query(
                    InvitedTable.TABLE_NAME,
                    cols2,
                    InvitedTable._ID + " = ?",
                    new String[]{String.valueOf(lineID)},
                    null,
                    null,
                    null
            );

            c2.moveToFirst();
            selected.remove(String.valueOf(Integer.parseInt(c2.getString(2))));
            //Log.e("MainActivity", "6666    "+ selected);
        }
    }
}