package com.example.nadav.finalproject;

import android.provider.BaseColumns;

public final class Constants
{
    private Constants()
    {
        throw new AssertionError("Can't create constants class");
    }

    public static abstract class InvitedTable implements BaseColumns
    {
        public static final String TABLE_NAME = "INVITEDTable";
        public static final String NAME = "Name";
        public static final String PHONE = "Phone";
        public static final String INVITED = "NumberOfInvited";
        public static final String COMING = "isComing";
    }


    public static abstract class ToDoList implements BaseColumns
    {
        public static final String TABLE_NAME = "ToDoList";
        public static final String TASK = "Task";
    }
}