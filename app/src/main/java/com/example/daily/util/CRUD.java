package com.example.daily.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.daily.Others.DailyTask;
import com.example.daily.MyDataBase.NoteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CRUD {
    SQLiteOpenHelper dbHandler;
    SQLiteDatabase db;

    private static final String[] columns = {
            NoteDatabase.ID,
            NoteDatabase.TITLE,
            NoteDatabase.CONTENT,
            NoteDatabase.TIME,
            NoteDatabase.TAG,
            NoteDatabase.STATE
    };

    public CRUD(Context context) {
        dbHandler = new NoteDatabase(context);
    }

    public void open(){
        db = dbHandler.getWritableDatabase();
    }

    public void close(){
        dbHandler.close();
    }

    //把note 加入到database里面
    public DailyTask addNote(DailyTask note){
        //add a note object to database
        ContentValues contentValues = new ContentValues();
        contentValues.put(NoteDatabase.TITLE, note.getTitle());
        contentValues.put(NoteDatabase.CONTENT, note.getContent());
        contentValues.put(NoteDatabase.TIME, note.getTime());
        contentValues.put(NoteDatabase.TAG, note.getTag());
        contentValues.put(NoteDatabase.STATE, note.getState());
        long insertId = db.insert(NoteDatabase.TABLE_NAME, null, contentValues);
        note.setId(insertId);
        return note;
    }

    public DailyTask getNote(long id){
        //get a note from database using cursor index
        Cursor cursor = db.query(NoteDatabase.TABLE_NAME, null, NoteDatabase.ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);
        int index1,index2,index3,index4,index5;
        index1=cursor.getColumnIndex(NoteDatabase.TITLE);
        index2=cursor.getColumnIndex(NoteDatabase.CONTENT);
        index3=cursor.getColumnIndex(NoteDatabase.TIME);
        index4=cursor.getColumnIndex(NoteDatabase.TAG);
        index5=cursor.getColumnIndex(NoteDatabase.STATE);

        if (cursor != null)
            cursor.moveToFirst();
        cursor.getColumnIndex(NoteDatabase.ID);
        DailyTask e = new DailyTask(cursor.getString(index1), cursor.getString(index2),cursor.getString(index3), cursor.getInt(index4),cursor.getString(index5));
        e.setId(id);
        return e;
    }

    public List<DailyTask> getAllNotes(){
        Cursor cursor = db.query(NoteDatabase.TABLE_NAME, null, null, null, null, null, null);

        List<DailyTask> notes = new ArrayList<>();
        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                DailyTask note = new DailyTask();
                note.setId(cursor.getLong(cursor.getColumnIndex(NoteDatabase.ID)));
                note.setTitle(cursor.getString(cursor.getColumnIndex(NoteDatabase.TITLE)));
                note.setContent(cursor.getString(cursor.getColumnIndex(NoteDatabase.CONTENT)));
                note.setTime(cursor.getString(cursor.getColumnIndex(NoteDatabase.TIME)));
                note.setTag(cursor.getInt(cursor.getColumnIndex(NoteDatabase.TAG)));
                note.setState(cursor.getString(cursor.getColumnIndex(NoteDatabase.STATE)));
                notes.add(note);
            }
        }
        return notes;
    }
    public List<DailyTask> getStateNotes(String state){
        Cursor cursor = db.query(NoteDatabase.TABLE_NAME, null, NoteDatabase.STATE + "=?", new String[] {state}, null, null, null);

        List<DailyTask> notes = new ArrayList<>();
        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                DailyTask note = new DailyTask();
                note.setId(cursor.getLong(cursor.getColumnIndex(NoteDatabase.ID)));
                note.setTitle(cursor.getString(cursor.getColumnIndex(NoteDatabase.TITLE)));
                note.setContent(cursor.getString(cursor.getColumnIndex(NoteDatabase.CONTENT)));
                note.setTime(cursor.getString(cursor.getColumnIndex(NoteDatabase.TIME)));
                note.setTag(cursor.getInt(cursor.getColumnIndex(NoteDatabase.TAG)));
                note.setState(cursor.getString(cursor.getColumnIndex(NoteDatabase.STATE)));
                notes.add(note);
            }
        }
        return notes;
    }
    public int updateNote(DailyTask note) {
        //update the info of an existing note
        ContentValues values = new ContentValues();
        values.put(NoteDatabase.TITLE,note.getTitle());
        values.put(NoteDatabase.CONTENT, note.getContent());
        values.put(NoteDatabase.TIME, note.getTime());
        values.put(NoteDatabase.TAG, note.getTag());
        values.put(NoteDatabase.STATE,note.getState());
        //updating row
        return db.update(NoteDatabase.TABLE_NAME, values,
                NoteDatabase.ID + "=?", new String[] { String.valueOf(note.getId())});
    }

    public void removeNote(long id){
        //remove a note according to ID value
        db.delete(NoteDatabase.TABLE_NAME, NoteDatabase.ID + "=" + id, null);
    }


}
