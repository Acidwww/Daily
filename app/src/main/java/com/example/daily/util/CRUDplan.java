package com.example.daily.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.daily.MyDataBase.MyPlanDatabase;
import com.example.daily.MyDataBase.NoteDatabase;
import com.example.daily.Others.DailyTask;
import com.example.daily.Others.Plan;

import java.util.ArrayList;
import java.util.List;

public class CRUDplan {
    SQLiteOpenHelper dbHandler;
    SQLiteDatabase db;

    private static final String[] columns = {
            MyPlanDatabase.ID,
            MyPlanDatabase.TAG,
            MyPlanDatabase.CONTENT,
            MyPlanDatabase.TIME,
            MyPlanDatabase.MON,
            MyPlanDatabase.TUES,
            MyPlanDatabase.WED,
            MyPlanDatabase.THURS,
            MyPlanDatabase.FRI,
            MyPlanDatabase.SAT,
            MyPlanDatabase.SUN,
            MyPlanDatabase.STATE,
            MyPlanDatabase.WEEK

};

    public CRUDplan(Context context) {
        dbHandler = new MyPlanDatabase(context);
    }

    public void open(){
        db = dbHandler.getWritableDatabase();
    }

    public void close(){
        dbHandler.close();
    }

    //把note 加入到database里面
    public Plan addNote(Plan note){
        //add a note object to database
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyPlanDatabase.TAG, note.getTag());
        contentValues.put(MyPlanDatabase.CONTENT, note.getContent());
        contentValues.put(MyPlanDatabase.TIME, note.getTime());
        contentValues.put(MyPlanDatabase.MON, note.getMonday());
        contentValues.put(MyPlanDatabase.TUES, note.getTuesday());
        contentValues.put(MyPlanDatabase.WED, note.getWednesday());
        contentValues.put(MyPlanDatabase.THURS, note.getThursday());
        contentValues.put(MyPlanDatabase.FRI, note.getFriday());
        contentValues.put(MyPlanDatabase.SAT, note.getSaturday());
        contentValues.put(MyPlanDatabase.SUN, note.getSunday());
        contentValues.put(MyPlanDatabase.STATE, note.getState());
        contentValues.put(MyPlanDatabase.WEEK, note.getWeek());

        long insertId = db.insert(MyPlanDatabase.TABLE_NAME, null, contentValues);
        note.setId(insertId);
        return note;
    }

    public List<Plan> getStateNotes(String state){
        Cursor cursor = db.query(MyPlanDatabase.TABLE_NAME, null, MyPlanDatabase.STATE + "=?", new String[] {state}, null, null, null);

        List<Plan> notes = new ArrayList<>();
        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                Plan note = new Plan();
                note.setId(cursor.getInt(cursor.getColumnIndex(MyPlanDatabase.ID)));
                note.setTag(cursor.getInt(cursor.getColumnIndex(MyPlanDatabase.TAG)));
                note.setContent(cursor.getString(cursor.getColumnIndex(MyPlanDatabase.CONTENT)));
                note.setTime(cursor.getString(cursor.getColumnIndex(MyPlanDatabase.TIME)));
                note.setMonday(cursor.getInt(cursor.getColumnIndex(MyPlanDatabase.MON)));
                note.setTuesday(cursor.getInt(cursor.getColumnIndex(MyPlanDatabase.TUES)));
                note.setWednesday(cursor.getInt(cursor.getColumnIndex(MyPlanDatabase.WED)));
                note.setThursday(cursor.getInt(cursor.getColumnIndex(MyPlanDatabase.THURS)));
                note.setFriday(cursor.getInt(cursor.getColumnIndex(MyPlanDatabase.FRI)));
                note.setSaturday(cursor.getInt(cursor.getColumnIndex(MyPlanDatabase.SAT)));
                note.setSunday(cursor.getInt(cursor.getColumnIndex(MyPlanDatabase.SUN)));
                note.setState(cursor.getString(cursor.getColumnIndex(MyPlanDatabase.STATE)));
                note.setWeek(cursor.getInt(cursor.getColumnIndex(MyPlanDatabase.WEEK)));
                notes.add(note);
            }
        }
        return notes;
    }

    public List<Plan> getAllNotes(){
        Cursor cursor = db.query(MyPlanDatabase.TABLE_NAME, null, null, null, null, null, null);

        List<Plan> notes = new ArrayList<>();
        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                Plan note = new Plan();
                note.setId(cursor.getInt(cursor.getColumnIndex(MyPlanDatabase.ID)));
                note.setTag(cursor.getInt(cursor.getColumnIndex(MyPlanDatabase.TAG)));
                note.setContent(cursor.getString(cursor.getColumnIndex(MyPlanDatabase.CONTENT)));
                note.setTime(cursor.getString(cursor.getColumnIndex(MyPlanDatabase.TIME)));
                note.setMonday(cursor.getInt(cursor.getColumnIndex(MyPlanDatabase.MON)));
                note.setTuesday(cursor.getInt(cursor.getColumnIndex(MyPlanDatabase.TUES)));
                note.setWednesday(cursor.getInt(cursor.getColumnIndex(MyPlanDatabase.WED)));
                note.setThursday(cursor.getInt(cursor.getColumnIndex(MyPlanDatabase.THURS)));
                note.setFriday(cursor.getInt(cursor.getColumnIndex(MyPlanDatabase.FRI)));
                note.setSaturday(cursor.getInt(cursor.getColumnIndex(MyPlanDatabase.SAT)));
                note.setSunday(cursor.getInt(cursor.getColumnIndex(MyPlanDatabase.SUN)));
                note.setState(cursor.getString(cursor.getColumnIndex(MyPlanDatabase.STATE)));
                note.setWeek(cursor.getInt(cursor.getColumnIndex(MyPlanDatabase.WEEK)));
                notes.add(note);
            }
        }
        return notes;
    }

    public Plan getNote(long id){
        //get a note from database using cursor index
        Cursor cursor = db.query(MyPlanDatabase.TABLE_NAME, null, MyPlanDatabase.ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        int index1=cursor.getInt(cursor.getColumnIndex(MyPlanDatabase.TAG));
        String index2=cursor.getString(cursor.getColumnIndex(MyPlanDatabase.CONTENT));
        String index3=cursor.getString(cursor.getColumnIndex(MyPlanDatabase.TIME));
        int index4=cursor.getInt(cursor.getColumnIndex(MyPlanDatabase.MON));
        int index5=cursor.getInt(cursor.getColumnIndex(MyPlanDatabase.TUES));
        int index6=cursor.getInt(cursor.getColumnIndex(MyPlanDatabase.WED));
        int index7=cursor.getInt(cursor.getColumnIndex(MyPlanDatabase.THURS));
        int index8=cursor.getInt(cursor.getColumnIndex(MyPlanDatabase.FRI));
        int index9=cursor.getInt(cursor.getColumnIndex(MyPlanDatabase.SAT));
        int index10=cursor.getInt(cursor.getColumnIndex(MyPlanDatabase.SUN));
        String index11=cursor.getString(cursor.getColumnIndex(MyPlanDatabase.STATE));
        int index12=cursor.getInt(cursor.getColumnIndex(MyPlanDatabase.WEEK));
        Plan e = new Plan(index1,index2,index3,index4,index5,index6,index7,index8,index9,index10,index11,index12);
        e.setId(id);
        return e;
    }



    public int getCount() {
        Cursor cursor = db.rawQuery("select count(_id) from "+MyPlanDatabase.TABLE_NAME,null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    public int updateNote(Plan note) {
        //update the info of an existing note
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyPlanDatabase.TAG, note.getTag());
        contentValues.put(MyPlanDatabase.CONTENT, note.getContent());
        contentValues.put(MyPlanDatabase.TIME, note.getTime());
        contentValues.put(MyPlanDatabase.MON, note.getMonday());
        contentValues.put(MyPlanDatabase.TUES, note.getTuesday());
        contentValues.put(MyPlanDatabase.WED, note.getWednesday());
        contentValues.put(MyPlanDatabase.THURS, note.getThursday());
        contentValues.put(MyPlanDatabase.FRI, note.getFriday());
        contentValues.put(MyPlanDatabase.SAT, note.getSaturday());
        contentValues.put(MyPlanDatabase.SUN, note.getSunday());
        contentValues.put(MyPlanDatabase.STATE, note.getState());
        contentValues.put(MyPlanDatabase.WEEK, note.getWeek());
        //updating row
        return db.update(MyPlanDatabase.TABLE_NAME, contentValues,
                MyPlanDatabase.ID + "=?", new String[] { String.valueOf(note.getId())});
    }

    public void removeNote(long id){
        //remove a note according to ID value
        db.delete(MyPlanDatabase.TABLE_NAME, MyPlanDatabase.ID + "=" + id, null);
    }
}
