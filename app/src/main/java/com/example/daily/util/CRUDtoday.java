package com.example.daily.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.daily.MyDataBase.MyTodayDatabase;

import com.example.daily.Others.Today;



public class CRUDtoday {
    SQLiteOpenHelper dbHandler;
    SQLiteDatabase db;

    private static final String[] columns = {
            MyTodayDatabase.ID,
            MyTodayDatabase.TODAY
    };

    public CRUDtoday(Context context) {
        dbHandler = new MyTodayDatabase(context);
    }

    public void open(){
        db = dbHandler.getWritableDatabase();
    }

    public void close(){
        dbHandler.close();
    }

    //把note 加入到database里面
    public Today addNote(Today note){
        //add a note object to database
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyTodayDatabase.TODAY, note.getToday());
        long insertId = db.insert(MyTodayDatabase.TABLE_NAME, null, contentValues);
        note.setId(insertId);
        return note;
    }

    public Today getNote(long id){
        //get a note from database using cursor index
        Cursor cursor = db.query(MyTodayDatabase.TABLE_NAME, null, MyTodayDatabase.ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        Today e = new Today(cursor.getString(cursor.getColumnIndex(MyTodayDatabase.TODAY)));
        e.setId(id);
        return e;
    }



    public int getCount() {
        Cursor cursor = db.rawQuery("select count(_id) from "+MyTodayDatabase.TABLE_NAME,null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    public int updateNote(Today note) {
        //update the info of an existing note
        ContentValues values = new ContentValues();
        values.put(MyTodayDatabase.TODAY,note.getToday());

        //updating row
        return db.update(MyTodayDatabase.TABLE_NAME, values,
                MyTodayDatabase.ID + "=?", new String[] { String.valueOf(note.getId())});
    }

    public void removeNote(long id){
        //remove a note according to ID value
        db.delete(MyTodayDatabase.TABLE_NAME, MyTodayDatabase.ID + "=" + id, null);
    }


}
