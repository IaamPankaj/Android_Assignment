package com.example.notesassignment.mydb;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.notesassignment.models.Note;

import java.util.ArrayList;
import java.util.List;

public class MyDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "my_notes.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "notes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_DATE + " TEXT NOT NULL, "
                + COLUMN_TITLE + " TEXT NOT NULL, "
                + COLUMN_DESCRIPTION + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long create(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, note.getDate());
        values.put(COLUMN_TITLE, note.getTitle());
        values.put(COLUMN_DESCRIPTION, note.getDescription());
        long id = db.insert(TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public Note read(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_DATE, COLUMN_TITLE, COLUMN_DESCRIPTION},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        assert cursor != null;
        @SuppressLint("Range")
        Note note = new Note(
                cursor.getLong(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_DATE)),
                cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
        );
        cursor.close();
        db.close();
        return note;
    }

    public List<Note> readAll() {
        List<Note> notes = new ArrayList<>();
        String selectAll = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAll, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Note note = new Note(
                        cursor.getLong(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DATE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
                );
                notes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }

    public int update(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, note.getDate());
        values.put(COLUMN_TITLE, note.getTitle());
        values.put(COLUMN_DESCRIPTION, note.getDescription());
        int rowsAffected = db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(note.getId())});
        db.close();
        return rowsAffected;
    }

    public int delete(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(note.getId())});
        db.close();
        return rowsAffected;
    }
}