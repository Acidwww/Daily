package com.example.daily.MyDataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyPlanDatabase extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "MyPlan1";
    public static final String ID = "_id";
    public static final String TAG = "tag";
    public static final String CONTENT = "content";
    public static final String TIME = "time";
    public static final String MON = "Monday";
    public static final String TUES = "Tuesday";
    public static final String WED = "Wednesday";
    public static final String THURS = "Thursday";
    public static final String FRI = "Firday";
    public static final String SAT = "Saturday";
    public static final String SUN = "Sunday";
    public static final String STATE = "state";//状态
    public static final String WEEK = "week";//判断是否修改循环日期

    public MyPlanDatabase(Context context){
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME
                + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TAG + " INTEGER DEFAULT 0,"
                + CONTENT + " TEXT NOT NULL,"
                + TIME + " TEXT NOT NULL,"
                + MON + " INTEGER DEFAULT 0,"
                + TUES + " INTEGER DEFAULT 0,"
                + WED + " INTEGER DEFAULT 0,"
                + THURS + " INTEGER DEFAULT 0,"
                + FRI + " INTEGER DEFAULT 0,"
                + SAT + " INTEGER DEFAULT 0,"
                + SUN + " INTEGER DEFAULT 0,"
                + STATE + " TEXT NOT NULL,"
                + WEEK + " INTEGER DEFAULT 0)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
