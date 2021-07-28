package com.example.daily.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.daily.Others.DailyTask;
import com.example.daily.Others.NoteDatabase;

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
            NoteDatabase.TAG
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
        long insertId = db.insert(NoteDatabase.TABLE_NAME, null, contentValues);
        note.setId(insertId);
        return note;
    }

    public DailyTask getNote(long id){
        //get a note from database using cursor index
        Cursor cursor = db.query(NoteDatabase.TABLE_NAME, columns, NoteDatabase.ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        DailyTask e = new DailyTask(cursor.getString(cursor.getColumnIndex(NoteDatabase.TITLE)), cursor.getString(cursor.getColumnIndex(NoteDatabase.CONTENT)),cursor.getString(cursor.getColumnIndex(NoteDatabase.TIME)), cursor.getInt(cursor.getColumnIndex(NoteDatabase.TAG)));
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
        //updating row
        return db.update(NoteDatabase.TABLE_NAME, values,
                NoteDatabase.ID + "=?", new String[] { String.valueOf(note.getId())});
    }

    public void removeNote(long id){
        //remove a note according to ID value
        db.delete(NoteDatabase.TABLE_NAME, NoteDatabase.ID + "=" + id, null);
    }


}
